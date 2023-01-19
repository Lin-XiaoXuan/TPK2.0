package com.njustjjy.tpk2.exception;

public class BusinessNotFindException extends TPKRuntimeException{
    public BusinessNotFindException() {
        super();
    }

    public BusinessNotFindException(String message) {
        super(message);
    }

    public BusinessNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessNotFindException(Throwable cause) {
        super(cause);
    }

    protected BusinessNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
