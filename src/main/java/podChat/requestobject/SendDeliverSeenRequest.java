package podChat.requestobject;


public class SendDeliverSeenRequest extends GeneralRequestObject {
    private long messageId;
    private long ownerId;

    SendDeliverSeenRequest(Builder builder) {
        super(builder);
        this.messageId = builder.messageId;
        this.ownerId = builder.ownerId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long messageId;
        private long ownerId;


        public Builder messageId(long messageId) {
            this.messageId = messageId;
            return this;
        }


        public Builder ownerId(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }


        public SendDeliverSeenRequest build() {
            return new SendDeliverSeenRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long count) {
        this.messageId = messageId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long offset) {
        this.ownerId = ownerId;
    }
}
