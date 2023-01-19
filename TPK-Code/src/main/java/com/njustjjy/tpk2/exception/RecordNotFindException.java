package com.njustjjy.tpk2.exception;

public class RecordNotFindException extends TPKRuntimeException{
    public RecordNotFindException() {
        super();
    }

    public RecordNotFindException(String message) {
        super(message);
    }

    public RecordNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFindException(Throwable cause) {
        super(cause);
    }

    protected RecordNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
