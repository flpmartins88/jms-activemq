package io.flpmartins.jms.activemq.queue;

import java.util.Scanner;
import javax.jms.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * Admin do ActiveMQ
 * http://localhost:8161/admin
 */

public class Receiver {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queueTasks = (Destination) context.lookup("Tasks");
        MessageConsumer consumer = session.createConsumer(queueTasks);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(message);
            }
        });

        new Scanner(System.in).nextLine();

        session.close();

        connection.close();
        context.close();
    }

}
