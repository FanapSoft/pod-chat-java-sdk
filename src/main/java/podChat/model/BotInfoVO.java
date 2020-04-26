package podChat.model;

import java.util.List;

public class BotInfoVO {
    private long botUserId;
    private String botName;
    private List<String> commandList;

    public long getBotUserId() {
        return botUserId;
    }

    public void setBotUserId(long botUserId) {
        this.botUserId = botUserId;
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
