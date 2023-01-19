package com.njustjjy.tpk2.exception;

public class EmpNotOnlineException extends TPKRuntimeException{
    public EmpNotOnlineException() {
    }

    public EmpNotOnlineException(String message) {
        super(message);
    }

    public EmpNotOnlineException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmpNotOnlineException(Throwable cause) {
        super(cause);
    }

    public EmpNotOnlineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
