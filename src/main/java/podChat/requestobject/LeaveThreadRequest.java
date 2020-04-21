package podChat.requestobject;


public class LeaveThreadRequest extends GeneralRequestObject {

    private long threadId;

     LeaveThreadRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;

    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public static class Builder extends GeneralRequestObject.Builder {
        private long threadId;

        public Builder(long threadId) {
            this.threadId = threadId;
        }

        public LeaveThreadRequest build() {
            return new LeaveThreadRequest(this);
        }

        @Override
        protected GeneralRequestObject.Builder self() {
            return this;
        }
    }

}
