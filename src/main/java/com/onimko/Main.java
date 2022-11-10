package com.onimko;


import com.onimko.broker.Consumer;
import com.onimko.broker.Producer;
import com.onimko.services.JsonMapperServices;
import com.onimko.services.PojoGenerator;
import com.onimko.util.LoadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final LoadProperties properties = new LoadProperties("myAws.properties");
    public static void main(String[] args) throws IOException {
        Producer producer = new Producer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));
        Consumer consumer = new Consumer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));

        //BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 10; i++)
        {
            System.out.println("Enter Msg, end to terminate:");
         //   String s = b.readLine();
         //   if (s.equals("end"))  break;
            producer.sendMessage(JsonMapperServices.toJson(PojoGenerator.getPojo()));
            System.out.println("Message successfully sent.");
            System.out.println(i + " Received:" + JsonMapperServices.toPojo(consumer.receiveMessage()));
        }

        producer.stop();
        consumer.stop();
    }

}
