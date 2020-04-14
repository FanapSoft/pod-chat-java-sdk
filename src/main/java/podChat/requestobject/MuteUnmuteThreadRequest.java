package podChat.requestobject;


public class MuteUnmuteThreadRequest extends GeneralRequestObject {
    private long threadId;

    MuteUnmuteThreadRequest(Builder builder) {
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

        public MuteUnmuteThreadRequest build() {
            return new MuteUnmuteThreadRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
