package io.flpmartins.jms.activemq.queue2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ObjectSender {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = (Destination) context.lookup("Tasks");
        MessageProducer producer = session.createProducer(queue);

        Item item = new Item(1L, "Teclado", 1);
        Pedido pedido = new Pedido();
        pedido.addItem(item);

        producer.send(session.createObjectMessage(pedido));

        connection.close();
        context.close();
    }
}
