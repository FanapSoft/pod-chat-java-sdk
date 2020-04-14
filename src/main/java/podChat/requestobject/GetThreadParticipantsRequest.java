package podChat.requestobject;


public class GetThreadParticipantsRequest extends GeneralRequestObject {
    private long count;
    private long offset;
    private long threadId;
    private String name;

    GetThreadParticipantsRequest(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.offset = builder.offset;
        this.threadId = builder.threadId;
        this.name=builder.name;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private long count;
        private long offset;
        private long threadId;
        private String name;

        public Builder(long threadId) {
            this.threadId = threadId;
        }


        public Builder count(long count) {
            this.count = count;
            return this;
        }

        public Builder offset(long offset) {
            this.offset = offset;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public GetThreadParticipantsRequest build() {
            return new GetThreadParticipantsRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
