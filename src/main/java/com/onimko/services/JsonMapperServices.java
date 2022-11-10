package com.onimko.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onimko.pojo.PojoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;

public class JsonMapperServices {
    private static final Logger log = LoggerFactory.getLogger(JsonMapperServices.class);
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    private JsonMapperServices() {
    }

    public static String toJson(PojoMessage pojo){
        String msg = null;
        try {
            msg = mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.warn("Json error of toString",e);
        }
        return msg;
    }

    public static PojoMessage toPojo(String json) {
        PojoMessage pojo = null;
        try {
            pojo = mapper.readValue(json, PojoMessage.class);
        } catch (JsonProcessingException e) {
            log.warn("Json error of to PojoMessage",e);
        }
        return pojo;
    }

}
