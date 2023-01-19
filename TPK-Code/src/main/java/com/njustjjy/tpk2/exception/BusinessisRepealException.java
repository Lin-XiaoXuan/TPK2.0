package com.njustjjy.tpk2.exception;

public class BusinessisRepealException extends TPKRuntimeException{
    public BusinessisRepealException() {
        super();
    }

    public BusinessisRepealException(String message) {
        super(message);
    }

    public BusinessisRepealException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessisRepealException(Throwable cause) {
        super(cause);
    }

    protected BusinessisRepealException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
