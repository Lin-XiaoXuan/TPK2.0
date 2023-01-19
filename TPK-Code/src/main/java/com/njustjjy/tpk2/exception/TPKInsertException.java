package com.njustjjy.tpk2.exception;

public class TPKInsertException extends TPKRuntimeException{
    public TPKInsertException() {
        super();
    }

    public TPKInsertException(String message) {
        super(message);
    }

    public TPKInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public TPKInsertException(Throwable cause) {
        super(cause);
    }

    protected TPKInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
