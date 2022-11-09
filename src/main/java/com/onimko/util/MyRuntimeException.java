package com.onimko.util;

import java.io.IOException;

public class MyRuntimeException extends RuntimeException{

    public MyRuntimeException(Exception e) {
        super(e);
    }
}
