package com.onimko;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.jms.Message;

public class Consumer {
    private MessageConsumer consumer;
    private Connection connection;
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);


    public Consumer() {
        try {
            ConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(Producer.QUEUE);
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            log.error("Consumer wasn't connection!", e);
        }
    }

    public String receiveMessage(){
        try{
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                return textMessage.getText();
            }
        } catch (JMSException e) {
            log.warn("Message don't send",e);
        }

       return null;
    }

    public void stop(){
        try{
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("Consumer connection & session don't stopped!",e);
            throw new RuntimeException(e);
        }
    }
}
