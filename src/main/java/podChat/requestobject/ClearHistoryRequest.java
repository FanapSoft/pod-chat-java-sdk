package podChat.requestobject;

public class ClearHistoryRequest extends GeneralRequestObject {

    private long threadId;

    private ClearHistoryRequest(Builder builder) {
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

        public ClearHistoryRequest build() {
            return new ClearHistoryRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }


}
