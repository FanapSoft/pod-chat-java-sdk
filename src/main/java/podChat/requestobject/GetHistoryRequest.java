package podChat.requestobject;


public class GetHistoryRequest extends BaseRequestObject {
    private long threadId;
    private String order; // ASC OR DESC
    private long userId;
    private long messageId;
    private long fromTime;
    private long fromTimeNanos;
    private long toTime;
    private long toTimeNanos;
    private String[] uniqueIds;
    private String query;
    private String allMentioned;
    private String unreadMentioned;
    private String metadataCriteria;

    GetHistoryRequest(Builder builder) {
        super(builder);
        this.threadId = builder.threadId;
        this.order = builder.order;
        this.userId = builder.userId;
        this.messageId = builder.messageId;
        this.fromTime = builder.fromTime;
        this.fromTimeNanos = builder.fromTimeNanos;
        this.toTime = builder.toTime;
        this.toTimeNanos = builder.toTimeNanos;
        this.uniqueIds = builder.uniqueIds;
        this.query = builder.query;
        this.allMentioned = builder.allMentioned;
        this.unreadMentioned = builder.unreadMentioned;
        this.metadataCriteria = builder.metadataCriteria;

    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getFromTimeNanos() {
        return fromTimeNanos;
    }

    public void setFromTimeNanos(long fromTimeNanos) {
        this.fromTimeNanos = fromTimeNanos;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public long getToTimeNanos() {
        return toTimeNanos;
    }

    public void setToTimeNanos(long toTimeNanos) {
        this.toTimeNanos = toTimeNanos;
    }

    public String[] getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(String[] uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAllMentioned() {
        return allMentioned;
    }

    public void setAllMentioned(String allMentioned) {
        this.allMentioned = allMentioned;
    }

    public String getUnreadMentioned() {
        return unreadMentioned;
    }

    public void setUnreadMentioned(String unreadMentioned) {
        this.unreadMentioned = unreadMentioned;
    }

    public String getMetadataCriteria() {
        return metadataCriteria;
    }

    public void setMetadataCriteria(String metadataCriteria) {
        this.metadataCriteria = metadataCriteria;
    }

    public static class Builder extends BaseRequestObject.Builder<Builder> {
        private long threadId;
        private String order;
        private long userId;
        private long messageId;
        private long fromTime;
        private long fromTimeNanos;
        private long toTime;
        private long toTimeNanos;
        private String[] uniqueIds;
        private String query;
        private String allMentioned;
        private String unreadMentioned;
        private String metadataCriteria;

        public Builder(long threadId) {
            this.threadId = threadId;
        }


        public Builder fromTime(long fromTime) {
            this.fromTime = fromTime;
            return this;
        }

        public Builder fromTimeNanos(long fromTimeNanos) {
            this.fromTimeNanos = fromTimeNanos;
            return this;
        }


        public Builder toTime(long toTime) {
            this.toTime = toTime;
            return this;
        }


        public Builder toTimeNanos(long toTimeNanos) {
            this.toTimeNanos = toTimeNanos;
            return this;
        }


        public Builder order(String order) {
            this.order = order;
            return this;
        }


        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder id(long id) {
            this.messageId = id;
            return this;
        }

        public Builder uniqueIds(String[] uniqueIds) {
            this.uniqueIds = uniqueIds;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }


        public Builder allMentioned(String allMentioned) {
            this.allMentioned = allMentioned;
            return this;
        }

        public Builder unreadMentioned(String unreadMentioned) {
            this.unreadMentioned = unreadMentioned;
            return this;
        }


        public Builder metadataCriteria(String metadataCriteria) {
            this.metadataCriteria = metadataCriteria;
            return this;
        }

        public GetHistoryRequest build() {
            return new GetHistoryRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
