package com.njustjjy.tpk2.exception;

public class TPKUpdateException extends TPKRuntimeException{
    public TPKUpdateException() {
        super();
    }

    public TPKUpdateException(String message) {
        super(message);
    }

    public TPKUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TPKUpdateException(Throwable cause) {
        super(cause);
    }

    protected TPKUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
