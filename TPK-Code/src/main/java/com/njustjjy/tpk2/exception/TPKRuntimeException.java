package com.njustjjy.tpk2.exception;

/**
 * 系统运行时异常父类
 *
 * */
public class TPKRuntimeException extends RuntimeException{
    public TPKRuntimeException() {
        super();
    }

    public TPKRuntimeException(String message) {
        super(message);
    }

    public TPKRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TPKRuntimeException(Throwable cause) {
        super(cause);
    }

    protected TPKRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
