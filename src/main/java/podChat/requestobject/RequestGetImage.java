package podChat.requestobject;


public class RequestGetImage {
    //    private long imageId;
    private String hashCode;
    //    private boolean downloadable;
    private String outputPath;
    private String size;
    private Float quality;
    private Boolean crop;

    RequestGetImage(Builder builder) {
//        this.imageId = builder.imageId;
        this.hashCode = builder.hashCode;
//        this.downloadable = builder.downloadable;
        this.outputPath = builder.outputPath;
        this.size = String.valueOf(builder.size);
        this.crop = builder.crop;
        this.quality = builder.quality;
    }

    public static class Builder {
        //        private long imageId;
        private String hashCode;
        //        private boolean downloadable;
        private String outputPath;
        private int size;
        private Float quality;
        private Boolean crop;


        public Builder(String hashCode, String outputPath) {
//            this.imageId = imageId;
            this.hashCode = hashCode;
//            this.downloadable = downloadable;
            this.outputPath = outputPath;
        }

        public RequestGetImage build() {
            return new RequestGetImage(this);
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }

    public Boolean getCrop() {
        return crop;
    }

    public void setCrop(Boolean crop) {
        this.crop = crop;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

//    public long getImageId() {
//        return imageId;
//    }
//
//    public void setImageId(long imageId) {
//        this.imageId = imageId;
//    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

//    public boolean isDownloadable() {
//        return downloadable;
//    }
//
//    public void setDownloadable(boolean downloadable) {
//        this.downloadable = downloadable;
//    }
}
