package podChat.requestobject;

public class RequestReplyMessage extends GeneralRequestObject {
    private String messageContent;
    private long threadId;
    private long messageId;
    private String systemMetadata;
    private int messageType;

    RequestReplyMessage(Builder builder) {
        super(builder);
        this.systemMetadata = builder.systemMetadata;
        this.messageContent = builder.messageContent;
        this.threadId = builder.threadId;
        this.messageId = builder.messageId;
        this.messageType = builder.messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getSystemMetadata() {
        return systemMetadata;
    }

    public void setSystemMetadata(String systemMetadata) {
        this.systemMetadata = systemMetadata;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String messageContent;
        private long threadId;
        private long messageId;
        private String systemMetadata;
        private int messageType;

        public Builder(String messageContent, long threadId, long messageId, int messageType) {
            this.messageContent = messageContent;
            this.threadId = threadId;
            this.messageId = messageId;
            this.messageType = messageType;
        }


        public Builder systemMetaData(String systemMetaData) {
            this.systemMetadata = systemMetaData;
            return this;
        }

        public RequestReplyMessage build() {
            return new RequestReplyMessage(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

}
