package com.njustjjy.tpk2.exception;

public class IdcardNotRegisterException extends TPKRuntimeException{
    public IdcardNotRegisterException() {
        super();
    }

    public IdcardNotRegisterException(String message) {
        super(message);
    }

    public IdcardNotRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdcardNotRegisterException(Throwable cause) {
        super(cause);
    }

    protected IdcardNotRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
