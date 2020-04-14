package podChat.requestobject;

public class ReplyTextMessageRequest extends GeneralRequestObject {
    private String content;
    private long threadId;
    private long repliedTo;
    private String systemMetaData;
    private int messageType;

    ReplyTextMessageRequest(Builder builder) {
        super(builder);
        this.systemMetaData = builder.systemMetaData;
        this.content = builder.content;
        this.threadId = builder.threadId;
        this.repliedTo = builder.repliedTo;
        this.messageType = builder.messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getSystemMetaData() {
        return systemMetaData;
    }

    public void setSystemMetaData(String systemMetaData) {
        this.systemMetaData = systemMetaData;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String content;
        private long threadId;
        private long repliedTo;
        private String systemMetaData;
        private int messageType;

        public Builder(String content, long threadId, long repliedTo, int messageType) {
            this.content = content;
            this.threadId = threadId;
            this.repliedTo = repliedTo;
            this.messageType = messageType;
        }


        public Builder systemMetaData(String systemMetaData) {
            this.systemMetaData = systemMetaData;
            return this;
        }

        public ReplyTextMessageRequest build() {
            return new ReplyTextMessageRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

}
