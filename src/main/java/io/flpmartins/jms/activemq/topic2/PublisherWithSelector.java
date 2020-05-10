package io.flpmartins.jms.activemq.topic2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PublisherWithSelector {

    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        // Por causa do client id e do durable subscriber
        // a mensagem não será perdida caso o consumidor não esteja de pé
        connection.setClientID("gerencia");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("Avisos");
        MessageProducer producer = session.createProducer(topico);

        Message message = session.createTextMessage("Um aviso qualquer aqui");
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }

}
