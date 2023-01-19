package com.njustjjy.tpk2.exception;

/**
 * 查询时发生异常
 * */
public class TPKSelectException extends TPKRuntimeException{
    public TPKSelectException() {
    }

    public TPKSelectException(String message) {
        super(message);
    }

    public TPKSelectException(String message, Throwable cause) {
        super(message, cause);
    }

    public TPKSelectException(Throwable cause) {
        super(cause);
    }

    public TPKSelectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
