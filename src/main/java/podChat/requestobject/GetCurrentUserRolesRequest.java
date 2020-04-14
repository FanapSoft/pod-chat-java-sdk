package podChat.requestobject;


public class GetCurrentUserRolesRequest extends GeneralRequestObject {
    private long threadId;

    GetCurrentUserRolesRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long threadId;


        public Builder(long threadId) {
            this.threadId = threadId;
        }

        public GetCurrentUserRolesRequest build() {
            return new GetCurrentUserRolesRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
