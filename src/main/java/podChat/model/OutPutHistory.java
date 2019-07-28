package podChat.model;


public class OutPutHistory {
    private boolean hasError;
    private String errorMessage;
    private long errorCode;
    private ResultsHistory result;


       public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public ResultsHistory getResult() {
        return result;
    }

    public void setResult(ResultsHistory result) {
        this.result = result;
    }
}
