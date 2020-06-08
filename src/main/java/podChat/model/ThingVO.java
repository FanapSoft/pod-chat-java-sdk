package podChat.model;

public class ThingVO {
    private long id;
    private String name;
    private String title;
    private String type;
    private boolean active;
    private boolean chatSendEnable;
    private boolean chatReceiveEnable;
    private Owner owner;
    private Owner creator;
    private boolean bot;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isChatSendEnable() {
        return chatSendEnable;
    }

    public void setChatSendEnable(boolean chatSendEnable) {
        this.chatSendEnable = chatSendEnable;
    }

    public boolean isChatReceiveEnable() {
        return chatReceiveEnable;
    }

    public void setChatReceiveEnable(boolean chatReceiveEnable) {
        this.chatReceiveEnable = chatReceiveEnable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getCreator() {
        return creator;
    }

    public void setCreator(Owner creator) {
        this.creator = creator;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}