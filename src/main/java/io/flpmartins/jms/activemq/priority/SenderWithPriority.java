package io.flpmartins.jms.activemq.priority;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * Prioridades, modo não persistente, TTL
 *
 * Prioridade -> quanto maior mais importante é a mensagem
 *   Obs: é necessário adicionar <policyEntry queue=">" prioritizedMessages="true"/>
 *        dentro da tag policyEntries em: apache-activemq-5.12.0/conf/activemq.xml
 *
 * NON_PERSISTENT -> as mensagens não são armazenadas no banco do activemq
 * TTL -> Caso a mensagem não tenha sido consumida até o tempo informado, ela é descartada
 */
public class SenderWithPriority {

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination queue = (Destination) context.lookup("logs");
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("[INFO]"), DeliveryMode.NON_PERSISTENT, 1, 3000);
        producer.send(session.createTextMessage("[WARN]"), DeliveryMode.NON_PERSISTENT, 2, 5000);
        producer.send(session.createTextMessage("[ERROR]"), DeliveryMode.NON_PERSISTENT, 3, 10000);

        connection.close();
        context.close();
    }

}
