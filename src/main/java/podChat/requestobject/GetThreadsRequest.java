package podChat.requestobject;

import java.util.ArrayList;

public class GetThreadsRequest extends BaseRequestObject {

    private ArrayList<Integer> threadIds;
    private String threadName;
    private long creatorCoreUserId;
    private long partnerCoreUserId;
    private boolean isNew;

    GetThreadsRequest(Builder builder) {
        super(builder);
        this.threadIds = builder.threadIds;
        this.threadName = builder.threadName;
        this.creatorCoreUserId = builder.creatorCoreUserId;
        this.partnerCoreUserId = builder.partnerCoreUserId;
        this.isNew = builder.isNew;
    }

    public ArrayList<Integer> getThreadIds() {
        return threadIds;
    }

    public void setThreadIds(ArrayList<Integer> threadIds) {
        this.threadIds = threadIds;
    }

    public long getCreatorCoreUserId() {
        return creatorCoreUserId;
    }

    public void setCreatorCoreUserId(long creatorCoreUserId) {
        this.creatorCoreUserId = creatorCoreUserId;
    }

    public long getPartnerCoreUserId() {
        return partnerCoreUserId;
    }

    public void setPartnerCoreUserId(long partnerCoreUserId) {
        this.partnerCoreUserId = partnerCoreUserId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isNew() {
        return isNew;
    }


    public static class Builder extends BaseRequestObject.Builder<Builder> {
        private ArrayList<Integer> threadIds;
        private String threadName;
        private long creatorCoreUserId;
        private long partnerCoreUserId;
        private boolean isNew;


        public Builder threadName(String threadName) {
            this.threadName = threadName;
            return this;
        }

        public Builder creatorCoreUserId(long creatorCoreUserId) {
            this.creatorCoreUserId = creatorCoreUserId;
            return this;
        }


        public Builder partnerCoreUserId(long partnerCoreUserId) {
            this.partnerCoreUserId = partnerCoreUserId;
            return this;
        }


        public Builder threadIds(ArrayList<Integer> threadIds) {
            this.threadIds = threadIds;
            return this;
        }

        public Builder newMessages() {
            this.isNew = true;
            return this;
        }


        public GetThreadsRequest build() {
            return new GetThreadsRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }
}
