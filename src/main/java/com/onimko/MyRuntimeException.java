package com.onimko;

import java.io.IOException;

public class MyRuntimeException extends RuntimeException{

    public MyRuntimeException(Exception e) {
        super(e);
    }
}
