package podChat.requestobject;

public class DeleteMessageRequest extends GeneralRequestObject {

    private long messageId;
    private boolean deleteForAll;

    private DeleteMessageRequest(Builder builder) {
        super(builder);
        this.deleteForAll = builder.deleteForAll;
        this.messageId = builder.messageId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private boolean deleteForAll;
        private long messageId;


        public Builder(long messageId) {
            this.messageId = messageId;
        }

        public Builder messageIds(long messageId) {
            this.messageId = messageId;
            return this;
        }


        public Builder deleteForAll(boolean deleteForAll) {
            this.deleteForAll = deleteForAll;
            return this;
        }


        public DeleteMessageRequest build() {
            return new DeleteMessageRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }

    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public boolean isDeleteForAll() {
        return deleteForAll;
    }

    public void setDeleteForAll(boolean deleteForAll) {
        this.deleteForAll = deleteForAll;
    }

}


