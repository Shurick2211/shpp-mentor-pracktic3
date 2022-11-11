package com.onimko.services;

import com.onimko.pojo.PojoMessage;
import junit.framework.TestCase;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActionServicesTest extends TestCase {
    MainActionServices services = new MainActionServices(3,"test.properties");
    public void testGenerateMessage() {
        Stream<PojoMessage> stream = services.generateMessage();
        List<PojoMessage> list = stream.collect(Collectors.toList());
        assertEquals(3, list.size());
        assertEquals(list.get(0).getClass(), PojoMessage.class);
        assertNotNull(list.get(list.size() - 1));
    }
    public void testSendMessage() {
        assertTrue(services.sendMessage());
    }
}