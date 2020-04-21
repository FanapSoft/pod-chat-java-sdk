package podChat.requestobject;


public class GetImageRequest {
    private long imageId;
    private String hashCode;
    private boolean downloadable;
    private String outputPath;

    GetImageRequest(Builder builder) {
        this.imageId = builder.imageId;
        this.hashCode = builder.hashCode;
        this.downloadable = builder.downloadable;
        this.outputPath = builder.outputPath;
    }

    public static class Builder {
        private long imageId;
        private String hashCode;
        private boolean downloadable;
        private String outputPath;


        public Builder(long imageId, String hashCode, boolean downloadable, String outputPath) {
            this.imageId = imageId;
            this.hashCode = hashCode;
            this.downloadable = downloadable;
            this.outputPath = outputPath;
        }

        public GetImageRequest build() {
            return new GetImageRequest(this);
        }
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }
}
