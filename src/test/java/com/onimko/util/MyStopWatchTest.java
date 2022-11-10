package com.onimko.util;

import junit.framework.TestCase;

public class MyStopWatchTest extends TestCase {

    public void testStart(){
       MyStopWatch sw = new MyStopWatch(3);
       sw.start();
       for (int i = 0; i < 6; i++){
           assertFalse(sw.isEndsCont());
           try {
               Thread.sleep(500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       assertTrue(sw.isEndsCont());
    }
}