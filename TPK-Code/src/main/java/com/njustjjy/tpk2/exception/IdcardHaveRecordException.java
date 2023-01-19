package com.njustjjy.tpk2.exception;

public class IdcardHaveRecordException extends TPKRuntimeException{
    public IdcardHaveRecordException() {
        super();
    }

    public IdcardHaveRecordException(String message) {
        super(message);
    }

    public IdcardHaveRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdcardHaveRecordException(Throwable cause) {
        super(cause);
    }

    protected IdcardHaveRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
