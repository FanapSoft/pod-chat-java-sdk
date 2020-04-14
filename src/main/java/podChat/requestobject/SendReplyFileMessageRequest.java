package podChat.requestobject;

public class SendReplyFileMessageRequest extends GeneralRequestObject {

    private String content;
    private long threadId;
    private long repliedTo;
    private String systemMetaData;
    private String filePath;
    private int messageType;
    private int xC;
    private int yC;
    private int hC;
    private int wC;


    SendReplyFileMessageRequest(Builder builder) {
        super(builder);
        this.systemMetaData = builder.systemMetaData;
        this.content = builder.content;
        this.threadId = builder.threadId;
        this.repliedTo = builder.repliedTo;
        this.filePath = builder.filePath;
        this.messageType = builder.messageType;
        this.xC = builder.xC;
        this.yC = builder.yC;
        this.hC = builder.hC;
        this.wC = builder.wC;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(long repliedTo) {
        this.repliedTo = repliedTo;
    }

    public String getSystemMetaData() {
        return systemMetaData;
    }

    public void setSystemMetaData(String systemMetaData) {
        this.systemMetaData = systemMetaData;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getxC() {
        return xC;
    }

    public SendReplyFileMessageRequest setxC(int xC) {
        this.xC = xC;
        return this;
    }

    public int getyC() {
        return yC;
    }

    public SendReplyFileMessageRequest setyC(int yC) {
        this.yC = yC;
        return this;
    }

    public int gethC() {
        return hC;
    }

    public SendReplyFileMessageRequest sethC(int hC) {
        this.hC = hC;
        return this;
    }

    public int getwC() {
        return wC;
    }

    public SendReplyFileMessageRequest setwC(int wC) {
        this.wC = wC;
        return this;
    }

    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String content;
        private long threadId;
        private long repliedTo;
        private String systemMetaData;
        private String filePath;
        private int messageType;
        private int xC;
        private int yC;
        private int hC;
        private int wC;


        public Builder(String content, long threadId, long repliedTo, String filePath, int messageType) {
            this.content = content;
            this.threadId = threadId;
            this.repliedTo = repliedTo;
            this.filePath = filePath;
            this.messageType = messageType;
        }

        public Builder systemMetaData(String systemMetaData) {
            this.systemMetaData = systemMetaData;
            return this;
        }

        public Builder xC(int xC) {
            this.xC = xC;
            return this;
        }

        public Builder yC(int yC) {
            this.yC = yC;
            return this;
        }

        public Builder wC(int wC) {
            this.wC = wC;
            return this;
        }

        public Builder hC(int hC) {
            this.hC = hC;
            return this;
        }

        public SendReplyFileMessageRequest build() {
            return new SendReplyFileMessageRequest(this);
        }


        @Override
        protected Builder self() {
            return this;
        }

    }
}
