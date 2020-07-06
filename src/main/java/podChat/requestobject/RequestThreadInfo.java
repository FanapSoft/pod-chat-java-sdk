package podChat.requestobject;


public class RequestThreadInfo extends GeneralRequestObject {
    private String image;
    private long threadId;
    private String title;
    //    private String name;
    private String description;
    private String metadata;
    private String imageHashFile;

    RequestThreadInfo(Builder builder) {
        super(builder);
        this.description = builder.description;
        this.image = builder.image;
        this.metadata = builder.metadata;
//        this.name = builder.name;
        this.threadId = builder.threadId;
        this.title = builder.title;
        this.imageHashFile = builder.imageHashFile;
    }

    public String getTitle() {
        return title;
    }


    public static class Builder extends GeneralRequestObject.Builder<Builder> {
        private String image;
        //        private String name;
        private String description;
        private String metadata;
        private long threadId;
        private String title;
        private String imageHashFile;


        public Builder image(String image) {
            this.image = image;
            return this;
        }


        public Builder threadId(long threadId) {
            this.threadId = threadId;
            return this;
        }

//
//        public Builder name(String name) {
//            this.name = name;
//            return this;
//        }


        public Builder description(String description) {
            this.description = description;
            return this;
        }


        public Builder metadat(String metadata) {
            this.metadata = metadata;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder imageHashFile(String imageHashFile) {
            this.imageHashFile = imageHashFile;
            return this;
        }

        public RequestThreadInfo build() {
            return new RequestThreadInfo(this);
        }


        @Override
        protected Builder self() {
            return this;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageHashFile() {
        return imageHashFile;
    }

    public void setImageHashFile(String imageHashFile) {
        this.imageHashFile = imageHashFile;
    }
}
