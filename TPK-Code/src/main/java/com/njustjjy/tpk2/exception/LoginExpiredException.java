package com.njustjjy.tpk2.exception;

public class LoginExpiredException extends RuntimeException{
    public LoginExpiredException() {
        super();
    }

    public LoginExpiredException(String message) {
        super(message);
    }

    public LoginExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginExpiredException(Throwable cause) {
        super(cause);
    }

    public LoginExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
