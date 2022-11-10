package com.onimko.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyStopWatch extends Thread {
    private static final Logger log = LoggerFactory.getLogger(MyStopWatch.class);
    private static final int MILLI = 1000;
    private final int second;
    private boolean result;

    public MyStopWatch(int second) {
        this.second = second;
        result = false;
    }


    public boolean isEndsCont(){
        return result;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) second * MILLI);
        } catch (InterruptedException e) {
            log.warn("Interrupted",e);
        }
        result = true;
    }
}
