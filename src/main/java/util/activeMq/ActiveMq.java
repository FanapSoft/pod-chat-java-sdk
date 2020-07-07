package util.activeMq;


import config.QueueConfigVO;
import exception.ConnectionException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import podChat.chat.Chat;

import javax.jms.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.IllegalStateException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


/**
 * Created By Khojasteh on 7/24/2019
 */
public class ActiveMq {
    private static Logger logger = LogManager.getLogger(ActiveMq.class);
    ConnectionFactory factory;
    IoAdapter ioAdapter;
    private MessageProducer producer;
    private MessageConsumer consumer;
    private Session proSession;
    private Session conSession;
    private Connection proConnection;
    private Connection conConnection;
    private Destination inputQueue;
    private Destination outputQueue;
    private QueueConfigVO queueConfigVO;
    private AtomicBoolean reconnect = new AtomicBoolean(false);

    public ActiveMq(final IoAdapter ioAdapter, QueueConfigVO queueConfigVO) throws ConnectionException {
        this.ioAdapter = ioAdapter;
        this.queueConfigVO = queueConfigVO;

        inputQueue = new QueueImpl(queueConfigVO.getQueueInput());
        outputQueue = new QueueImpl(queueConfigVO.getQueueOutput());

        factory = new ActiveMQConnectionFactory(
                queueConfigVO.getQueueUserName(),
                queueConfigVO.getQueuePassword(),
                new StringBuilder()
                        .append("failover:(")
                        .append(getSocketAddress(queueConfigVO.getUris()))
                        .append(")?jms.useAsyncSend=true")
                        .append("&randomize=true")
                        .append("&jms.sendTimeout=").append(queueConfigVO.getQueueReconnectTime())
                        .toString());

        if (factory != null) {
            connect();

        } else {
            logger.error("An exception occurred...");

            throw new ConnectionException(ConnectionException.ConnectionExceptionType.ACTIVE_MQ_CONNECTION);
        }
    }
public ActiveMq(){}
    private void connect() {
        if (reconnect.compareAndSet(false, true)) {

            while (true) {

                try {
                    this.proConnection = factory.createConnection(
                            queueConfigVO.getQueueUserName(),
                            queueConfigVO.getQueuePassword());

                    proConnection.start();

                    this.conConnection = factory.createConnection(
                            queueConfigVO.getQueueUserName(),
                            queueConfigVO.getQueuePassword());

                    conConnection.start();

                    proSession = proConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

                    conSession = conConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

                    producer = proSession.createProducer(outputQueue);

                    consumer = conSession.createConsumer(inputQueue);
                    consumer.setMessageListener(new QueueMessageListener());
                    conConnection.setExceptionListener(new QueueExceptionListener());
                    proConnection.setExceptionListener(new QueueExceptionListener());

                    proConnection.setExceptionListener(new QueueExceptionListener());
                    logger.info("connection established");


                    break;

                } catch (JMSException exception) {
                    logger.error("Reconnecting exception " + exception);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        logger.error(e1);
                    }
                    close();
                }

            }
            reconnect.set(false);
        }
    }

    public void sendMessage(String messageWrapperVO) throws JMSException, UnsupportedEncodingException, ConnectionException {

        byte[] bytes = messageWrapperVO.getBytes("utf-8");
        BytesMessage bytesMessage = proSession.createBytesMessage();
        bytesMessage.writeBytes(bytes);

        try {

            producer.send(bytesMessage);

        } catch (IllegalStateException e) {
            logger.error("An exception in sending message" + e);
            throw new ConnectionException(ConnectionException.ConnectionExceptionType.ACTIVE_MQ_SENDING_MESSAGE);

        } catch (Exception e) {
            logger.error("An exception in sending message:" + e);
            logger.error("closing connection");
            close();
            logger.error("Reconnecting to queue...");
            connect();
            producer.send(bytesMessage);
            logger.error("Sending message after reconnecting to queue: " + messageWrapperVO);

            throw new ConnectionException(ConnectionException.ConnectionExceptionType.ACTIVE_MQ_SENDING_MESSAGE);
        }
    }

    public void shutdown() throws JMSException {
        this.conConnection.close();
        this.proConnection.close();

        this.conSession.close();

        this.proSession.close();

    }

    public void close() {
        try {
            producer.close();
        } catch (JMSException e) {
            showErrorLog("An exception occurred at cloning producer: " + e);
        }
        try {

            consumer.close();

        } catch (Exception e) {
            showErrorLog("An exception occurred at closing consumer" + e);
        }
        try {
            proSession.close();
            conSession.close();


        } catch (Exception e) {
            showErrorLog("An exception occurred at closing session :" + e);

        }
        try {

            conConnection.close();
            proConnection.close();
        } catch (Exception e) {
            showErrorLog("An exception occurred at closing connection : " + e);

        }

        ioAdapter.onSessionCloseError();
    }

    private void showInfoLog(String i, String json) {
        if (Chat.isLoggable) logger.info(i + "\n \n" + json);

    }

    private void showInfoLog(String json) {
        if (Chat.isLoggable) logger.info("\n \n" + json);
    }

    private void showErrorLog(String i, String json) {
        if (Chat.isLoggable) logger.error(i + "\n \n" + json);
    }

    private void showErrorLog(String e) {
        if (Chat.isLoggable) logger.error("\n \n" + e);

    }

    private void showErrorLog(Throwable throwable) {
        if (Chat.isLoggable) logger.error("\n \n" + throwable.getMessage());
    }

    private String getSocketAddress(List<String> uris) {
        return String.join(",", uris.stream().map(s -> "tcp://" + s).collect(Collectors.toList()));
    }

    private class QueueMessageListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            try {
                message.acknowledge();

                if (message instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) message;
                    byte[] buffer = new byte[(int) bytesMessage.getBodyLength()];
                    int readBytes = bytesMessage.readBytes(buffer);

                    if (readBytes != bytesMessage.getBodyLength()) {
                        throw new IOException("Inconsistance message length");
                    }

                    String json = new String(buffer/*, "utf-8"*/);

                    ioAdapter.onReceiveMessage(json);

                }
            } catch (JMSException s) {
                try {
                    throw s;
                } catch (JMSException e) {
                    showErrorLog("jms Exception: " + e);
                }
            } catch (Throwable e) {
                showErrorLog("An exception occurred: " + e);
            }
        }
    }

    private class QueueExceptionListener implements ExceptionListener {
        @Override
        public void onException(JMSException exception) {
            close();
            showErrorLog("JMSException occurred: " + exception);
            try {
                Thread.sleep(queueConfigVO.getQueueReconnectTime());
                connect();
            } catch (InterruptedException e) {
                showErrorLog("An exception occurred: " + e);
            }
        }
    }

}
