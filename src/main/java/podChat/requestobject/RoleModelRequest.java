package podChat.requestobject;

import java.util.ArrayList;

public class RoleModelRequest {
    private long userId;
    private ArrayList<String> roles;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

}
