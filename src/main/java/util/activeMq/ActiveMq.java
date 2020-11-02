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
import java.util.ArrayList;
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
    private Session proSession;
    private Connection proConnection;
    private Destination inputQueue;
    private Destination outputQueue;
    private QueueConfigVO queueConfigVO;
    private List<QueueConsumer> consumerList;
    private int receivePoolSize;
    private int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
    private AtomicBoolean reconnect = new AtomicBoolean(false);

    public ActiveMq(final IoAdapter ioAdapter, QueueConfigVO queueConfigVO) throws ConnectionException {
        this.ioAdapter = ioAdapter;
        this.queueConfigVO = queueConfigVO;

        inputQueue = new QueueImpl(queueConfigVO.getQueueInput());
        outputQueue = new QueueImpl(queueConfigVO.getQueueOutput());

        this.receivePoolSize = queueConfigVO.getReceivePoolSize();
        if (queueConfigVO.getAcknowledgeMode() != null) {
            switch (queueConfigVO.getAcknowledgeMode()) {
                case Session.CLIENT_ACKNOWLEDGE:
                    acknowledgeMode = Session.CLIENT_ACKNOWLEDGE;
                    break;
                case Session.DUPS_OK_ACKNOWLEDGE:
                    acknowledgeMode = Session.DUPS_OK_ACKNOWLEDGE;
                    break;
                default:
                    acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
                    break;
            }
        }

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

    private void connect() {
        if (reconnect.compareAndSet(false, true)) {

            while (true) {

                try {
                    this.proConnection = factory.createConnection(
                            queueConfigVO.getQueueUserName(),
                            queueConfigVO.getQueuePassword());
                    proConnection.start();
                    proSession = proConnection.createSession(false, acknowledgeMode);
                    producer = proSession.createProducer(outputQueue);
                    proConnection.setExceptionListener(new QueueExceptionListener());

                    consumerList = new ArrayList<>(receivePoolSize);
                    for (int i = 0; i < receivePoolSize; i++) {
                        addConsumer(i);
                    }
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

        this.proConnection.close();
        this.proSession.close();

        for (QueueConsumer queueConsumer : consumerList) {
            queueConsumer.closeConnection();
            queueConsumer.closeSession();
        }
    }

    private void close() {
        try {
            producer.close();
        } catch (JMSException e) {
            showErrorLog("An exception occurred at cloning producer: " + e);
        }
        try {
            proSession.close();
        } catch (Exception e) {
            showErrorLog("An exception occurred at closing session :" + e);

        }
        try {
            proConnection.close();
        } catch (Exception e) {
            showErrorLog("An exception occurred at closing connection : " + e);
        }

        for (QueueConsumer queueConsumer : consumerList) {
            try {
                queueConsumer.close();
            } catch (Exception e) {
                showErrorLog("An exception occurred at closing consumer : " + e);
            }
        }

        ioAdapter.onSessionCloseError();
    }

    private void addConsumer(int serialNumber) throws JMSException {
        QueueConsumer queueConsumer = new QueueConsumer(
                factory.createConnection(queueConfigVO.getQueueUserName(), queueConfigVO.getQueuePassword()),
                inputQueue,
                serialNumber
        );
        consumerList.add(serialNumber, queueConsumer);
        queueConsumer.start();
        logger.info("Consumer " + serialNumber + " added");
    }

    private void closeAndReconnect(QueueConsumer queueConsumer) {
        consumerList.remove(queueConsumer.serial);
        try {
            queueConsumer.close();
        } catch (Exception e) {
            logger.warn(e);
        }
        boolean loop = true;
        do {
            try {
                addConsumer(queueConsumer.serial);
                loop = false;
            } catch (JMSException e) {
                logger.warn("An exception occurred", e);
            }
            try {
                Thread.sleep(queueConfigVO.getQueueReconnectTime());
            } catch (InterruptedException e) {
                logger.warn("An exception occurred", e);
            }
        } while (loop);
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
                if (acknowledgeMode == Session.CLIENT_ACKNOWLEDGE) {
                    message.acknowledge();
                }

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
    private class QueueConsumer extends Thread implements ExceptionListener {
        private int serial;
        private boolean loop = true;
        private Connection connection;
        private Session session;
        private MessageConsumer consumer;

        QueueConsumer(Connection connection, Destination queue, int serial) throws JMSException {
            this.serial = serial;
            this.connection = connection;
            this.session = this.connection.createSession(false, acknowledgeMode);
            this.consumer = session.createConsumer(queue);
            this.connection.start();
            connection.setExceptionListener(this);
        }

        @Override
        public void run() {
            while (loop) {
                try {
                    Message message = consumer.receive();
                    try {
                        if (acknowledgeMode == Session.CLIENT_ACKNOWLEDGE) {
                            message.acknowledge();
                        }
                        if (message instanceof BytesMessage) {
                            BytesMessage bytesMessage = (BytesMessage) message;
                            byte[] buffer = new byte[(int) bytesMessage.getBodyLength()];
                            int readBytes = bytesMessage.readBytes(buffer);

                            if (readBytes != bytesMessage.getBodyLength()) {
                                throw new IOException("Inconsistance message length");
                            }

                            String json = new String(buffer/*, "utf-8"*/);
                            showInfoLog("Consumer " + serial + " received Message!", json);
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
                } catch (Exception e) {
                    logger.warn("An exception occurred ", e);
                }
            }
        }

        @Override
        public void onException(JMSException exception) {
            logger.error(exception);
            closeAndReconnect(this);
        }

        void close() {
            loop = false;
            try {
                consumer.close();
            } catch (JMSException e) {
                showErrorLog("An exception occurred at closing consumer : " + e);
            }
            try {
                session.close();
            } catch (JMSException e) {
                showErrorLog("An exception occurred at closing session : " + e);
            }
            try {
                connection.close();
            } catch (JMSException e) {
                showErrorLog("An exception occurred at closing connection : " + e);
            }
        }

        void closeSession() {
            try {
                session.close();
            } catch (JMSException e) {
                showErrorLog("An exception occurred at closing session : " + e);
            }
        }

        void closeConnection() {
            try {
                connection.close();
            } catch (JMSException e) {
                showErrorLog("An exception occurred at closing connection : " + e);
            }
        }
    }

}
