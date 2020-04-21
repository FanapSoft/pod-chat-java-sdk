package podChat.requestobject;


public class GetFileRequest {
    private long fileId;
    private String hashCode;
    private boolean downloadable;
    private String outputPath;

    GetFileRequest(Builder builder) {
        this.fileId = builder.fileId;
        this.hashCode = builder.hashCode;
        this.downloadable = builder.downloadable;
        this.outputPath = builder.outputPath;
    }

    public static class Builder {
        private long fileId;
        private String hashCode;
        private boolean downloadable;
        private String outputPath;

        public Builder(long fileId, String hashCode, boolean downloadable, String outputPath) {
            this.fileId = fileId;
            this.hashCode = hashCode;
            this.downloadable = downloadable;
            this.outputPath = outputPath;
        }

        public GetFileRequest build() {
            return new GetFileRequest(this);
        }
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
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

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
