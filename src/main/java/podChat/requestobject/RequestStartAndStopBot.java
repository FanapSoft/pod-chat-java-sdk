package podChat.requestobject;

public class RequestStartAndStopBot extends GeneralRequestObject {
    private String botName;
    private long threadId;

    RequestStartAndStopBot(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
        this.botName = builder.botName;

    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String botName;
        private long threadId;

        public Builder(long threadId, String botName) {
            this.threadId = threadId;
            this.botName = botName;
        }


        public RequestStartAndStopBot build() {
            return new RequestStartAndStopBot(this);
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

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}