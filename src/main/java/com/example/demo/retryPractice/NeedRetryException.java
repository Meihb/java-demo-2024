package com.example.demo.retryPractice;

public class NeedRetryException extends RuntimeException {
    public NeedRetryException(String message, Throwable cause) {
        super(message, cause);
    }
    public NeedRetryException(String message) {
        super(message);
    }
}