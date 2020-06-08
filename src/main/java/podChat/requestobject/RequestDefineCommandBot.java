package podChat.requestobject;

import java.util.List;

public class RequestDefineCommandBot extends GeneralRequestObject {

    private String botName;
    private List<String> commandList;

    RequestDefineCommandBot(Builder builder) {
        super(builder);
        this.botName = builder.botName;
        this.commandList = builder.commandList;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String botName;
        private List<String> commandList;


        public Builder(String botName, List<String> commandList) {
            this.botName = botName;
            this.commandList = commandList;
        }


        public RequestDefineCommandBot build() {
            return new RequestDefineCommandBot(this);
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

    public List<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<String> commandList) {
        this.commandList = commandList;
    }
}