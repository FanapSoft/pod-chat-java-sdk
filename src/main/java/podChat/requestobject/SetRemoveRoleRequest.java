package podChat.requestobject;

import java.util.ArrayList;

public class SetRemoveRoleRequest extends GeneralRequestObject {

    private long threadId;
    private ArrayList<RoleModelRequest> userRoles;

    private SetRemoveRoleRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
        this.userRoles = builder.userRoles;
    }

    public ArrayList<RoleModelRequest> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(ArrayList<RoleModelRequest> userRoles) {
        this.userRoles = userRoles;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long threadId;
        private ArrayList<RoleModelRequest> userRoles;

        public Builder(long threadId, ArrayList<RoleModelRequest> userRoles) {
            this.threadId = threadId;
            this.userRoles = userRoles;
        }

        public SetRemoveRoleRequest build() {
            return new SetRemoveRoleRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }


}
