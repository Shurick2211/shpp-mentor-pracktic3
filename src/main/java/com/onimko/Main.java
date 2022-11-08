package com.onimko;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
            Producer producer = new Producer();

            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

            while(true)
            {
                System.out.println("Enter Msg, end to terminate:");
                String s = b.readLine();
                if (s.equals("end"))
                    break;
                producer.sendMessage(s);
                System.out.println("Message successfully sent.");
            }

            producer.stop();
    }

}
