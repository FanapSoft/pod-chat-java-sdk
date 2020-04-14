package podChat.requestobject;

public class SendTextMessageRequest extends GeneralRequestObject {
    private String textMessage;
    private int messageType;
    private String metaData;
    private long threadId;
    private long repliedTo;
    private String systemMetadata;

    SendTextMessageRequest(Builder builder) {
        super(builder);
        this.setTextMessage(builder.textMessage);
        this.setThreadId(builder.threadId);
        this.setMessageType(builder.messageType);
        this.setMetaData(builder.metaData);
        this.setRepliedTo(builder.repliedTo);
        this.setSystemMetadata(builder.systemMetadata);
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(long repliedTo) {
        this.repliedTo = repliedTo;
    }

    public String getSystemMetadata() {
        return systemMetadata;
    }

    public void setSystemMetadata(String systemMetadata) {
        this.systemMetadata = systemMetadata;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String textMessage;
        private int messageType;
        private String metaData;
        private long threadId;
        private long repliedTo;
        private String systemMetadata;

        public Builder(String textMessage, long threadId, int messageType) {
            this.textMessage = textMessage;
            this.threadId = threadId;
            this.messageType = messageType;
        }


        public Builder jsonMetaData(String jsonMetaData) {
            this.metaData = jsonMetaData;
            return this;
        }

        public Builder repliedTo(long repliedTo) {
            this.repliedTo = repliedTo;
            return this;
        }

        public Builder systemMetadata(String systemMetadata) {
            this.systemMetadata = systemMetadata;
            return this;
        }

        public SendTextMessageRequest build() {
            return new SendTextMessageRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }


}
