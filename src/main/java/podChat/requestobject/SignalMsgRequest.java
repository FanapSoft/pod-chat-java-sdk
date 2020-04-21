package podChat.requestobject;

public class SignalMsgRequest {

    private int signalType;
    private long threadId;

    SignalMsgRequest(Builder builder) {
        this.signalType = builder.signalType;
        this.threadId = builder.threadId;
    }

    public static class Builder {
        private int signalType;
        private long threadId;


        public Builder signalType(int signalType) {
            this.signalType = signalType;
            return this;
        }

        public Builder threadId(long threadId) {
            this.threadId = threadId;
            return this;
        }

        public SignalMsgRequest build() {
            return new SignalMsgRequest(this);
        }
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public int getSignalType() {
        return signalType;
    }

    public void setSignalType(int signalType) {
        this.signalType = signalType;
    }
}
