package com.njustjjy.tpk2.exception;

public class IdcardNotFindException extends TPKRuntimeException{
    public IdcardNotFindException() {
        super();
    }

    public IdcardNotFindException(String message) {
        super(message);
    }

    public IdcardNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdcardNotFindException(Throwable cause) {
        super(cause);
    }

    protected IdcardNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
