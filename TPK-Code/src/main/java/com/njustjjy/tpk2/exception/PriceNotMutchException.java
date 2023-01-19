package com.njustjjy.tpk2.exception;

//价格不匹配异常
public class PriceNotMutchException extends TPKRuntimeException{
    public PriceNotMutchException() {}

    public PriceNotMutchException(String message) {
        super(message);
    }

    public PriceNotMutchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceNotMutchException(Throwable cause) {
        super(cause);
    }

    public PriceNotMutchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
