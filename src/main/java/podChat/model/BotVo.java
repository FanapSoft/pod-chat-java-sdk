package podChat.model;

public class BotVo {
    private String 	apiToken;
    private ThingVO thingVO;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ThingVO getThingVO() {
        return thingVO;
    }

    public void setThingVO(ThingVO thingVO) {
        this.thingVO = thingVO;
    }
}