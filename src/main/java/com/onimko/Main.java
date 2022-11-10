package com.onimko;

import com.onimko.broker.Consumer;
import com.onimko.broker.Producer;
import com.onimko.services.ConsumerServices;
import com.onimko.services.JsonMapperServices;
import com.onimko.services.PojoGenerator;
import com.onimko.util.LoadProperties;
import com.onimko.util.MyStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Main {

    private static final int MIN_GEN = 20000;
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final LoadProperties properties = new LoadProperties("myApp.properties");
    public static void main(String[] args) {
        Producer producer = new Producer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));
        log.info("Producer was create!");
        Consumer consumer = new Consumer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));
        ConsumerServices consumerServices = new ConsumerServices(consumer);
        consumerServices.start();
        log.info("Consumer was create and run!");
        int n = args.length == 0 ? MIN_GEN: Integer.parseInt(args[0]);
        MyStopWatch stopWatch = new MyStopWatch(
                Integer.parseInt(properties.getProperty("second")));
        stopWatch.start();
        log.info("Stopwatch was started!");
        Stream.generate(PojoGenerator::getPojo).limit(n).takeWhile(p -> !stopWatch.isEndsCont())
                .forEach(p -> producer.sendMessage(JsonMapperServices.toJson(p)));
        log.info("Messages was sent!");
        consumerServices.stopServices();
        producer.stop();
        consumer.stop();
        log.info("The end!");
    }
}