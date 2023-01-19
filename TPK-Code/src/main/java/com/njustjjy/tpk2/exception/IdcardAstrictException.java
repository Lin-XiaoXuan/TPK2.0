package com.njustjjy.tpk2.exception;

public class IdcardAstrictException extends TPKRuntimeException{
    public IdcardAstrictException() {
        super();
    }

    public IdcardAstrictException(String message) {
        super(message);
    }

    public IdcardAstrictException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdcardAstrictException(Throwable cause) {
        super(cause);
    }

    protected IdcardAstrictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
