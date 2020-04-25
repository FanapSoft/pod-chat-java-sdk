package podChat.requestobject;

public class StartBotRequest extends GeneralRequestObject {
    private String botName;
    private long threadId;

    StartBotRequest(Builder builder) {
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


        public StartBotRequest build() {
            return new StartBotRequest(this);
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
