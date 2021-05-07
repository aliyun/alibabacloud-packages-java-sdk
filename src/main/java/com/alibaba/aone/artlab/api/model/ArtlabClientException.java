package com.alibaba.aone.artlab.api.model;

public class ArtlabClientException extends Exception {
    private final String errorCode;

    public ArtlabClientException(String errorCode) {
        this.errorCode = errorCode;
    }

    public ArtlabClientException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ArtlabClientException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ArtlabClientException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ArtlabClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
