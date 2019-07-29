package podChat.mainmodel;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class RequestThreadInnerMessage {

    private String text;
    private int type;
    private String metadata;
    private String systemMetadata;
    private List<Long> forwardedMessageIds;

    public RequestThreadInnerMessage(Builder builder) {
        this.text = builder.text;
        this.type = builder.type;
        this.metadata = builder.metadata;
        this.systemMetadata = builder.systemMetadata;
        this.forwardedMessageIds = builder.forwardedMessageIds;
    }

    public static class Builder {
        private String text;
        private int type;
        private String metadata;
        private String systemMetadata;
        private List<Long> forwardedMessageIds;

        public Builder message(String text) {
            this.text = text;
            return this;
        }

        @NotNull
        public Builder type(int type) {
            this.type = type;
            return this;
        }

        @NotNull
        public Builder metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        @NotNull
        public Builder systemMetadata(String systemMetadata) {
            this.systemMetadata = systemMetadata;
            return this;
        }

        @NotNull
        public Builder forwardedMessageIds(List<Long> forwardedMessageIds) {
            this.forwardedMessageIds = forwardedMessageIds;
            return this;
        }

        @NotNull
        public RequestThreadInnerMessage build() {
            return new RequestThreadInnerMessage(this);
        }

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getSystemMetadata() {
        return systemMetadata;
    }

    public void setSystemMetadata(String systemMetadata) {
        this.systemMetadata = systemMetadata;
    }

    public List<Long> getForwardedMessageIds() {
        return forwardedMessageIds;
    }

    public void setForwardedMessageIds(List<Long> forwardedMessageIds) {
        this.forwardedMessageIds = forwardedMessageIds;
    }
}
