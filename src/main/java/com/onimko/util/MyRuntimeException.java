package com.onimko.util;

public class MyRuntimeException extends RuntimeException{

    public MyRuntimeException(Exception e) {
        super(e);
    }
}
