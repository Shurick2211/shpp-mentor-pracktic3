package com.onimko.services;

import com.onimko.pojo.PojoMessage;
import junit.framework.TestCase;

public class PojoGeneratorTest extends TestCase {

    public void testGetPojo() {
        PojoMessage msg = null;
        for (int i = 0; i < 150; i++) {
            msg = PojoGenerator.getPojo();
            assertNotNull(msg);
        }
        assertNotNull(msg);
        assertEquals(msg.getClass(), PojoMessage.class);
        assertNotNull(msg.getName());
        assertNotNull(msg.getCreatedAt());
        assertEquals(153, msg.getCount());
    }
}