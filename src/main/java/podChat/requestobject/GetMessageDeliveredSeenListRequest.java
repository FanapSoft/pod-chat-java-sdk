package podChat.requestobject;


public class GetMessageDeliveredSeenListRequest extends BaseRequestObject {
    private long messageId;


    GetMessageDeliveredSeenListRequest(Builder builder) {
        super(builder);
        this.messageId = builder.messageId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public static class Builder extends BaseRequestObject.Builder<Builder> {
        private long messageId;

        public Builder(long messageId) {
            this.messageId = messageId;
        }


        public GetMessageDeliveredSeenListRequest build() {
            return new GetMessageDeliveredSeenListRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

}
