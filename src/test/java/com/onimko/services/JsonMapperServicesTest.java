package com.onimko.services;

import com.onimko.pojo.PojoMessage;
import junit.framework.TestCase;
import java.time.LocalDateTime;

public class JsonMapperServicesTest extends TestCase {
    private static final String json =
            "{\"name\":\"Name\",\"count\":1,\"createdAt\":\"2020-05-05T05:05:00\"}";
    private static final PojoMessage pojoMessage = new PojoMessage("Name",1,
            LocalDateTime.of(2020,5,5,5,5));

    public void testToJson() {
        assertEquals(json, JsonMapperServices.toJson(pojoMessage));
    }


    public void testToPojo() {
       assertEquals(pojoMessage, JsonMapperServices.toPojo(json));
    }
}