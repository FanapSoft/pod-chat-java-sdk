package podChat.requestobject;

public class RequestCreateBot extends GeneralRequestObject {

    private String botName;

    RequestCreateBot(Builder builder) {
        super(builder);
        this.botName = builder.botName;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String botName;


        public Builder(String botName) {
            this.botName = botName;
        }


        public RequestCreateBot build() {
            return new RequestCreateBot(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}