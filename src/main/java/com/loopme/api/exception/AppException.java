package com.loopme.api.exception;

public class AppException extends RuntimeException {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}