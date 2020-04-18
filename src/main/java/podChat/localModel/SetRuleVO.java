package podChat.localModel;

import podChat.requestobject.RoleModelRequest;

import java.util.ArrayList;

public class SetRuleVO {

    private long threadId;
    private ArrayList<RoleModelRequest> roles;
    private String typeCode;

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public ArrayList<RoleModelRequest> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<RoleModelRequest> roles) {
        this.roles = roles;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
