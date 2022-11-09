package com.onimko.broker;

import com.onimko.util.MyRuntimeException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.jms.Message;

public class Consumer {
    private MessageConsumer messageConsumer;
    private Connection connection;
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);


    public Consumer(String url, String user, String pass) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connectionFactory.setUserName(user);
            connectionFactory.setPassword(pass);

            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(Producer.QUEUE);
            messageConsumer = session.createConsumer(destination);
            log.debug("Consumer was created!");
        } catch (JMSException e) {
            log.error("Consumer wasn't connection!", e);
        }
    }

    public String receiveMessage(){
        try{
            Message message = messageConsumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                log.debug("Message was received: {}", textMessage.getText());
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
            throw new MyRuntimeException(e);
        }
    }
}
