package com.onimko.services;

import com.onimko.broker.Consumer;
import com.onimko.pojo.PojoMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ConsumerServices  extends Thread{

    private static final String SEPARATOR = ",";
    private static final Logger log = LoggerFactory.getLogger(ConsumerServices.class);
    private Consumer consumer;

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
        while (!isInterrupted()){
           pojo = JsonMapperServices.toPojo(consumer.receiveMessage());
           errors = validator.validate(pojo);
           if (errors.isEmpty()) saveCSVPojo(pojo);
           else saveCSVError(pojo, errors);
        }
    }

    private void saveCSVError(PojoMessage pojo, Set<ConstraintViolation<PojoMessage>> errors) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"errors\":[");
        stringBuilder.append(errors.stream().map(ConstraintViolation::getMessage).reduce((a,b) -> a + ", " + b).get());
        stringBuilder.append("]}");
        String str = pojo.getName() + SEPARATOR + pojo.getCount() + SEPARATOR + stringBuilder;
        log.info(str);
    }

    private void saveCSVPojo(PojoMessage pojo) {
        String str = pojo.getName() + SEPARATOR + pojo.getCount();
        log.info(str);
    }

    public void stopServices(){
        this.interrupt();
    }
}
