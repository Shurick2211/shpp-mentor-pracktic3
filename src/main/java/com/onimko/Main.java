package com.onimko;

import com.onimko.services.MainActionServices;

public class Main {
    private static final int MIN_GEN = 20000;
    private static final String PROPERTIES = "myApp.properties";
    public static void main(String[] args) {
        MainActionServices services =
                new MainActionServices(args.length == 0 ? MIN_GEN: Integer.parseInt(args[0]),
                        PROPERTIES);
        services.sendMessage();
    }
}