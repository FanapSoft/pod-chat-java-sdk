package podChat.requestobject;

public class RequestEditMessage extends GeneralRequestObject {
    private String messageContent;
    private long messageId;
    private String systemMetaData;

     RequestEditMessage( Builder builder) {
        super(builder);
        this.systemMetaData = builder.systemMetaData;
        this.messageContent = builder.messageContent;
        this.messageId = builder.messageId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String messageContent;
        private long messageId;
        private String systemMetaData;

        public Builder(String messageContent, long messageId) {
            this.messageContent = messageContent;
            this.messageId = messageId;
        }

        public Builder metaData(String metaData) {
            this.systemMetaData = metaData;
            return this;
        }

        public RequestEditMessage build() {
            return new RequestEditMessage(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getSystemMetaData() {
        return systemMetaData;
    }

    public void setSystemMetaData(String systemMetaData) {
        this.systemMetaData = systemMetaData;
    }
}
