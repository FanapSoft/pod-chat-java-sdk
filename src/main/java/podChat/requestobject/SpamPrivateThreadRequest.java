package podChat.requestobject;


public class SpamPrivateThreadRequest extends GeneralRequestObject {
    private long threadId;

    SpamPrivateThreadRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long threadId;

        public Builder(long threadId) {
            this.threadId = threadId;
        }


        public SpamPrivateThreadRequest build() {
            return new SpamPrivateThreadRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }
}
