package podChat.requestobject;

public class EditMessageRequest extends GeneralRequestObject {
    private String content;
    private long messageId;
    private String systemMetaData;

    EditMessageRequest(Builder builder) {
        super(builder);
        this.systemMetaData = builder.systemMetaData;
        this.content = builder.content;
        this.messageId = builder.messageId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String content;
        private long messageId;
        private String systemMetaData;


        public Builder(String content, long messageId) {
            this.content = content;
            this.messageId = messageId;
        }

        public Builder metaData(String metaData) {
            this.systemMetaData = metaData;
            return this;
        }


        public EditMessageRequest build() {
            return new EditMessageRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
