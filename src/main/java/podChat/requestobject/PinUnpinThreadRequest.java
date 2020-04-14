package podChat.requestobject;


public class PinUnpinThreadRequest extends GeneralRequestObject {
    private long threadId;

    PinUnpinThreadRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long threadId;

        public Builder(long threadId) {
            this.threadId = threadId;
        }

        public PinUnpinThreadRequest build() {
            return new PinUnpinThreadRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
