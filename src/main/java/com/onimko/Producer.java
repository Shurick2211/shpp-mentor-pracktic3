package com.onimko;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Producer {
    private MessageProducer messageProducer;
    private  TextMessage message;
    private Connection connection;
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    public static final String QUEUE = "MyQueue";
    public Producer(String url, String user, String pass) {
        try {
            ActiveMQConnectionFactory conFactory =
                    new ActiveMQConnectionFactory(url);
            conFactory.setUserName(user);
            conFactory.setPassword(pass);

            connection = conFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(QUEUE);

            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            message = session.createTextMessage();
            log.debug("Consumer was started!");
        }catch (JMSException e) {
            log.error("Error of Producer",e);
            throw new MyRuntimeException(e);
        }
    }

    public Producer sendMessage(String msg) {
        try {
            message.setText(msg);
            messageProducer.send(message);
        } catch (JMSException e) {
            log.warn("Message don't send",e);
        }
        return this;
    }

    public void stop(){
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("Producer connection & session don't stopped!",e);
            throw new MyRuntimeException(e);
        }
    }
}
