package podChat;

import podChat.chat.download_file.model.ResultDownloadFile;
import podChat.mainmodel.FileUpload;
import podChat.model.ChatResponse;
import podChat.model.ErrorOutPut;
import podChat.model.ResultFile;
import podChat.model.ResultImageFile;

//A callback for while file is being uploaded.
public abstract class ProgressHandler {

    public interface onProgress {
        default void onProgressUpdate(int bytesSent) {
        }

        default void onProgressUpdate(String uniqueId, int bytesSent, int totalBytesSent, int totalBytesToSend) {
        }

        default void onFinish(String imageJson, ChatResponse<ResultImageFile> chatResponse) {
        }

        default void onError(String jsonError, ErrorOutPut error) {
        }
    }

    public interface onProgressFile {
        void onProgressUpdate(int bytesSent);

        default void onProgress(String uniqueId, int bytesSent, int totalBytesSent, int totalBytesToSend) {
        }

        default void onFinish(String imageJson, FileUpload fileImageUpload) {
        }
        default void onImageFinish(String imageJson, ChatResponse<ResultImageFile> chatResponse) {
        }

        default void onError(String jsonError, ErrorOutPut error) {
        }
    }

    public interface sendFileMessage {

        default void onProgressUpdate(String uniqueId, int bytesSent, int totalBytesSent, int totalBytesToSend) {
        }

        default void onFinishImage(String json, ChatResponse<ResultImageFile> chatResponse) {
        }

        default void onFinishFile(String json, ChatResponse<ResultFile> chatResponse) {
        }

        default void onError(String jsonError, ErrorOutPut error) {
        }
    }
    public interface IDownloadFile {

        default void onProgressUpdate(String uniqueId, long bytesDownloaded, long totalBytesToDownload) {
        }

        default void onProgressUpdate(String uniqueId, int progress) {
        }

        void onError(String uniqueId, String error, String url);

        default void onLowFreeSpace(String uniqueId, String url) {
        }

        default void onFileReady(ChatResponse<ResultDownloadFile> response) {
        }


    }

    public interface cancelUpload {
        default void cancelUpload(String uniqueId) {

        }
    }

    public void onProgress(int bytesSent, int totalBytesSent, int totalBytesToSend) {
    }
}
