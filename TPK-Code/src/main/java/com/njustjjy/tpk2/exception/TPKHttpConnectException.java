package com.njustjjy.tpk2.exception;

/**
 * Http链接异常
 *
 *
 * */
public class TPKHttpConnectException extends TPKRuntimeException{
    public TPKHttpConnectException() {
        super();
    }

    public TPKHttpConnectException(String message) {
        super(message);
    }

    public TPKHttpConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public TPKHttpConnectException(Throwable cause) {
        super(cause);
    }

    protected TPKHttpConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
