package io.flpmartins.jms.activemq.queue;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender {

    private static final String EXIT_TEXT = "exit";

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = (Destination) context.lookup("Tasks");
        MessageProducer producer = session.createProducer(queue);

        Scanner scanner = new Scanner(System.in);

        String commandText = "";
        while (true) {
            Thread.sleep(100);

            commandText = scanner.nextLine();

            if (EXIT_TEXT.equals(commandText))
                break;

            producer.send(session.createTextMessage(commandText));

        }

        connection.close();
        context.close();
    }

}
