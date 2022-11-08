package com.onimko;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class Producer {
    private MessageProducer producer;
    private TextMessage message;
    private Connection connection;
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    public Producer() {

        try {
            ActiveMQConnectionFactory conFactory =
                    new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

            connection = conFactory.createConnection();
            connection.start();
            session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("MuQueue");

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            message = session.createTextMessage();
        }catch (JMSException e) {
            log.error("Error of Producer",e);
            throw new RuntimeException(e);
        }
    }

    public Producer sendMessage(String msg) {
        try {
            message.setText(msg);
            producer.send(message);
        } catch (JMSException e) {
            log.error("Message don't send",e);
            throw new RuntimeException(e);
        }
        return this;
    }

    public void stop(){
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("Connection & session don't send",e);
            throw new RuntimeException(e);
        }

    }
}
