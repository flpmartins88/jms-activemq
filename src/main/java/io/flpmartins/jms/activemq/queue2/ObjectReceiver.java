package io.flpmartins.jms.activemq.queue2;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ObjectReceiver {

    public static void main(String[] args) throws NamingException, JMSException {
        // Sem essa linha ele vai tentar consumir 6 vezes (padrão, e se der erro) e depois mover para a DLQ
        // O ActiveMQ precisa saber quais pacotes ele pode deserializar
        // Se não estiver permitido ele dá ClassNotFound
        // Se estiver e o tipo estiver errado ele dá erro de cast
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,java.util,io.flpmartins.jms.activemq.queue2");

        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Destination queueTasks = (Destination) context.lookup("Tasks");
        MessageConsumer consumer = session.createConsumer(queueTasks);

        consumer.setMessageListener(message -> {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                Pedido pedido = (Pedido) objectMessage.getObject();
                System.out.println(pedido);
                //message.acknowledge();

                //ack com session transaction
                session.commit();
                //session.rollback();
            } catch (JMSException e) {
                e.printStackTrace();
            }


        });

        new Scanner(System.in).nextLine();

        session.close();

        connection.close();
        context.close();
    }

}
