package podChat.requestobject;

/**
 * Created By Khojasteh on 8/25/2019
 */
public class UploadFileRequest {
    private String filePath;

    protected UploadFileRequest(Builder<?> builder) {
        filePath = builder.filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static class Builder<T extends Builder<T>> {

        private String filePath;

        public Builder(String filePath) {
            this.filePath = filePath;
        }

    /*    public T RequestUploadFile(StringBuilder filePath) {
            filePath = filePath;
            return (T) this;
        }*/

        public UploadFileRequest build() {
            return new UploadFileRequest(this);
        }
    }
}
