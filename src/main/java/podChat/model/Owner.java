package podChat.model;

import org.apache.commons.net.ntp.TimeStamp;

import java.util.List;

public class Owner {
    private Long id;
    private Long coreUserId;
    private Integer lastVersion;
    private String nickName;
    private Boolean active;
    private Boolean chatSendEnable;
    private Boolean chatReceiveEnable;
    private Boolean guest;
    private Integer issuerCode;
    private Long ssoId;
    private Long contactsLastSeenUpdate;
    private String profile;
    private Boolean bot;
    private String fullName;
    private String name;
    private String username;
    private ChatProfileVO chatProfileVO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCoreUserId() {
        return coreUserId;
    }

    public void setCoreUserId(Long coreUserId) {
        this.coreUserId = coreUserId;
    }

    public Integer getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(Integer lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getChatSendEnable() {
        return chatSendEnable;
    }

    public void setChatSendEnable(Boolean chatSendEnable) {
        this.chatSendEnable = chatSendEnable;
    }

    public Boolean getChatReceiveEnable() {
        return chatReceiveEnable;
    }

    public void setChatReceiveEnable(Boolean chatReceiveEnable) {
        this.chatReceiveEnable = chatReceiveEnable;
    }

    public Boolean getGuest() {
        return guest;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public Integer getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(Integer issuerCode) {
        this.issuerCode = issuerCode;
    }

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
        this.ssoId = ssoId;
    }

    public Long getContactsLastSeenUpdate() {
        return contactsLastSeenUpdate;
    }

    public void setContactsLastSeenUpdate(Long contactsLastSeenUpdate) {
        this.contactsLastSeenUpdate = contactsLastSeenUpdate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChatProfileVO getChatProfileVO() {
        return chatProfileVO;
    }

    public void setChatProfileVO(ChatProfileVO chatProfileVO) {
        this.chatProfileVO = chatProfileVO;
    }
}
