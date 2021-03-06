package config;

import java.util.List;

/**
 * Created By Khojasteh on 8/24/2019
 */
public class QueueConfigVO {

    private List<String> uris;
    private String queuePort;
    private String queueInput;
    private String queueOutput;
    private String queueUserName;
    private String queuePassword;
    private int queueReconnectTime;
    private Integer receivePoolSize;
    private Integer acknowledgeMode;

    public QueueConfigVO(List<String> uris, String queueInput, String queueOutput, String queueUserName, String queuePassword, Integer receivePoolSize, Integer acknowledgeMode) {
        this.uris = uris;
        this.queueInput = queueInput;
        this.queueOutput = queueOutput;
        this.queueUserName = queueUserName;
        this.queuePassword = queuePassword;
        this.queueReconnectTime = 20000;
        this.receivePoolSize = receivePoolSize;
        this.acknowledgeMode = acknowledgeMode;
    }

    public String getQueueInput() {
        return queueInput;
    }

    public void setQueueInput(String queueInput) {
        this.queueInput = queueInput;
    }

    public String getQueueOutput() {
        return queueOutput;
    }

    public void setQueueOutput(String queueOutput) {
        this.queueOutput = queueOutput;
    }


    public String getQueuePort() {
        return queuePort;
    }

    public void setQueuePort(String queuePort) {
        this.queuePort = queuePort;
    }

    public String getQueueUserName() {
        return queueUserName;
    }

    public void setQueueUserName(String queueUserName) {
        this.queueUserName = queueUserName;
    }

    public String getQueuePassword() {
        return queuePassword;
    }

    public void setQueuePassword(String queuePassword) {
        this.queuePassword = queuePassword;
    }

    public int getQueueReconnectTime() {
        return queueReconnectTime;
    }

    public void setQueueReconnectTime(int queueReconnectTime) {
        this.queueReconnectTime = queueReconnectTime;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public Integer getReceivePoolSize() {
        return receivePoolSize;
    }

    public void setReceivePoolSize(Integer receivePoolSize) {
        this.receivePoolSize = receivePoolSize;
    }

    public Integer getAcknowledgeMode() {
        return acknowledgeMode;
    }

    public void setAcknowledgeMode(Integer acknowledgeMode) {
        this.acknowledgeMode = acknowledgeMode;
    }
}
