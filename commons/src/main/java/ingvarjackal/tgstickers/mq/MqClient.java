package ingvarjackal.tgstickers.mq;

import com.google.gson.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.lang.IllegalStateException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

public class MqClient implements AutoCloseable, ExceptionListener {
    public enum Queue {
        BL_SERVICE_QUEUE("BL_SERVICE_QUEUE"), OUT_SERVICE_QUEUE("OUT_SERVICE_QUEUE");

        public final String queue;
        Queue(String queue) {
            this.queue = queue;
        }
    }

    private final static Gson gson = new Gson();

    private final ActiveMQConnectionFactory connectionFactory;
    {
        String uri = System.getenv("ACTIVEMQ_URI");
        if (uri == null) {
            throw new IllegalStateException("'ACTIVEMQ_URI' env var is not set!");
        }
        connectionFactory = new ActiveMQConnectionFactory(uri);
    }
    private final ThreadLocal<Connection> connection = ThreadLocal.withInitial(() -> {
        try {
            Connection conn = connectionFactory.createConnection();
            conn.setExceptionListener(this);
            conn.start();
            return conn;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    });
    private final ThreadLocal<Session> session = ThreadLocal.withInitial(() -> {
        try {
            return connection.get().createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    });
    private final ThreadLocal<HashMap<Queue, MessageProducer>> msgProducers = ThreadLocal.withInitial(() -> new HashMap<>());
    private final ThreadLocal<HashMap<Queue, MessageConsumer>> msgConsumers = ThreadLocal.withInitial(() -> new HashMap<>());

    private MessageProducer getProducer(Queue queue) throws JMSException {
        MessageProducer producer = msgProducers.get().get(queue);
        if (producer == null) {
            Destination destination = session.get().createQueue(queue.queue);
            producer = session.get().createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            msgProducers.get().put(queue, producer);
        }
        return producer;
    }

    private MessageConsumer getConsumer(Queue queue) throws JMSException {
        MessageConsumer consumer = msgConsumers.get().get(queue);
        if (consumer == null) {
            Destination destination = session.get().createQueue(queue.queue);
            consumer = session.get().createConsumer(destination);
            msgConsumers.get().put(queue, consumer);
        }
        return consumer;
    }

    public void put(Serializable serializable, Queue queue) throws JMSException {
        getProducer(queue).send(session.get().createTextMessage(gson.toJson(serializable, serializable.getClass())));
    }

    public TgStanza get(Queue queue) throws JMSException {
        Message msg = getConsumer(queue).receive();
        if (msg instanceof TextMessage) {
            return gson.fromJson(((TextMessage) msg).getText(), TgStanza.class);
        } else {
            throw new JMSException("Received wrong message class " + msg.getClass());
        }
    }

    public TgStanza get(Queue queue, int timeout) throws JMSException {
        Message msg = getConsumer(queue).receive(timeout);
        if (msg instanceof TextMessage) {
            return gson.fromJson(((TextMessage) msg).getText(), TgStanza.class);
        } else {
            throw new JMSException("Received wrong message class " + msg.getClass());
        }
    }

    @Override
    public void onException(JMSException e) {
        throw new RuntimeException(e);
    }

    @Override
    public void close() throws Exception {
        for (MessageConsumer consumer : msgConsumers.get().values()) {
            consumer.close();
        }
        session.get().close();
        connection.get().close();
        msgConsumers.remove();
        msgProducers.remove();
        session.remove();
        connection.remove();
    }
}
