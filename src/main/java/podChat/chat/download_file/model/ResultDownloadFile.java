package podChat.chat.download_file.model;

import retrofit2.http.Url;

import java.io.File;

public class ResultDownloadFile {
    private File file;

    private Url uri;

    private String hashCode;

    private long id;


    public void setFile(File file) {
        this.file = file;
    }

    public void setUri(Url uri) {
        this.uri = uri;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public Url getUri() {
        return uri;
    }

    public String getHashCode() {
        return hashCode;
    }

    public long getId() {
        return id;
    }

}
