package com.njustjjy.tpk2.exception;

public class BtpriceTableException extends TPKRuntimeException{
    public BtpriceTableException() {
    }

    public BtpriceTableException(String message) {
        super(message);
    }

    public BtpriceTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BtpriceTableException(Throwable cause) {
        super(cause);
    }

    public BtpriceTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
