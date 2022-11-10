package com.onimko.services;

import com.onimko.broker.Consumer;
import com.onimko.broker.Producer;
import com.onimko.util.LoadProperties;
import com.onimko.util.MyStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class MainActionServices {
    private static final Logger log = LoggerFactory.getLogger(MainActionServices.class);
    private static final LoadProperties properties = new LoadProperties("myApp.properties");
    private final int n;
    private Producer producer;
    private Consumer consumer;
    private ConsumerServices consumerServices;

    public MainActionServices(int n) {
        this.n = n;
    }

    public void action(){
        createProducer();
        setConsumerAndServices();
        generateMessage();
        end();
    }

    private void createProducer(){
        producer = new Producer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));
        log.info("Producer was create!");
    }
    private void setConsumerAndServices(){
        consumer = new Consumer(properties.getProperty("aws.broker.url"),
                properties.getProperty("aws.broker.user"),
                properties.getProperty("aws.broker.pass"));
        consumerServices = new ConsumerServices(consumer);
        consumerServices.start();
        log.info("Consumer was create and run!");
    }
    private void generateMessage() {
        MyStopWatch stopWatch = new MyStopWatch(
                Integer.parseInt(properties.getProperty("second")));
        stopWatch.start();
        log.info("Stopwatch was started!");

        Stream.generate(PojoGenerator::getPojo).limit(n).takeWhile(p -> !stopWatch.isEndsCont())
                .forEach(p -> producer.sendMessage(JsonMapperServices.toJson(p)));
        log.info("Messages was sent!");
    }

    private void end(){
        consumerServices.stopServices();
        producer.stop();
        consumer.stop();
        log.info("The end!");
    }
}
