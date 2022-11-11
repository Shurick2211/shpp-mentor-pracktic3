package com.onimko.services;

import com.onimko.broker.Consumer;
import com.onimko.broker.Producer;
import com.onimko.pojo.PojoMessage;
import com.onimko.util.LoadProperties;
import com.onimko.util.MyStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class MainActionServices {
    private static final Logger log = LoggerFactory.getLogger(MainActionServices.class);
    private static final String URL = "aws.broker.url";
    private static final String USER = "aws.broker.user";
    private static final String PASS = "aws.broker.pass";
    private final LoadProperties properties;
    private final int n;
    private Producer producer;
    private Consumer consumer;
    private ConsumerServices consumerServices;
    private MyStopWatch stopWatch;

    public MainActionServices(int n, String propFile) {
        this.n = n;
        properties = new LoadProperties(propFile);
        createProducer();
        runConsumerAndServices();
    }

    private void createProducer(){
        producer = new Producer(properties.getProperty(URL),
                properties.getProperty(USER),
                properties.getProperty(PASS));
        log.info("Producer was create!");
    }
    private void runConsumerAndServices(){
        consumer = new Consumer(properties.getProperty(URL),
                properties.getProperty(USER),
                properties.getProperty(PASS));
        consumerServices = new ConsumerServices(consumer);
        consumerServices.start();
        log.info("Consumer was create and run!");
    }

    private void createAndRunStopWatch() {
        stopWatch = new MyStopWatch(
                Integer.parseInt(properties.getProperty("second")));
        stopWatch.start();
        log.info("Stopwatch was started!");
    }

    public Stream<PojoMessage> generateMessage() {
        createAndRunStopWatch();
        return  Stream.generate(PojoGenerator::getPojo).limit(n).takeWhile(p -> !stopWatch.isEndsCont());
    }

    public boolean sendMessage(){
        generateMessage().forEach(p -> producer.sendMessage(JsonMapperServices.toJson(p)));
        log.info("Messages was sent!");
        end();
        return true;
    }

    private void end(){
        consumerServices.stopServices();
        producer.stop();
        consumer.stop();
        log.info("The end!");
    }
}
