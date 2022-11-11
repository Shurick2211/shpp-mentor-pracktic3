package com.onimko.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onimko.broker.Consumer;
import com.onimko.pojo.PojoMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class ConsumerServices  extends Thread{
    private static final String SEPARATOR = ",";
    private static final Logger log = LoggerFactory.getLogger(ConsumerServices.class);
    private final Consumer consumer;
    boolean isStop;
    private static final String VALID = "validPOJO.csv";
    private static final String INVALID = "errorPOJO.csv";
    public ConsumerServices(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        PojoMessage pojo;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PojoMessage>> errors;
        try(FileWriter writer = new FileWriter(VALID, false);
            FileWriter writerError = new FileWriter(INVALID, false)) {
            while (!isStop) {
                pojo = JsonMapperServices.toPojo(consumer.receiveMessage());
                errors = validator.validate(pojo);
                if (errors.isEmpty()) writer.append(stringCSVPojo(pojo));
                else writerError.append(stringCSVError(pojo, errors));
            }
            writerError.flush();
            writer.flush();
        }catch (IOException e){
            log.error("Error writing CSV",e);
        }
    }

    private String stringCSVError(PojoMessage pojo, Set<ConstraintViolation<PojoMessage>> errors) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString("errors:" + Arrays.toString(errors.toArray()));
        } catch (JsonProcessingException e) {
            log.warn("Don't write errors" ,e);
        }
        return pojo.getName() + SEPARATOR + pojo.getCount() + SEPARATOR + json + "\n";
    }

    private String stringCSVPojo(PojoMessage pojo) {
        return pojo.getName() + SEPARATOR + pojo.getCount()+"\n";
    }

    public void stopServices(){
        isStop = true;
    }
}
