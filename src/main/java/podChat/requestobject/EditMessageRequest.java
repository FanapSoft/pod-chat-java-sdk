package podChat.requestobject;

public class EditMessageRequest extends GeneralRequestObject {
    private String content;
    private long messageId;
    private String metaData;
    private String systemMetadata;

    EditMessageRequest(Builder builder) {
        super(builder);
        this.metaData = builder.metaData;
        this.content = builder.content;
        this.messageId = builder.messageId;
        this.systemMetadata = builder.systemMetadata;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String content;
        private long messageId;
        private String metaData;
        private String systemMetadata;

        public Builder(String content, long messageId) {
            this.content = content;
            this.messageId = messageId;
        }

        public Builder metaData(String metaData) {
            this.metaData = metaData;
            return this;
        }

        public Builder systemMetadata(String systemMetadata) {
            this.systemMetadata = systemMetadata;
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

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }


    public String getSystemMetadata() {
        return systemMetadata;
    }

    public void setSystemMetadata(String systemMetadata) {
        this.systemMetadata = systemMetadata;
    }
}
