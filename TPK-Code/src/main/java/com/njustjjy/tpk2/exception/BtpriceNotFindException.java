package com.njustjjy.tpk2.exception;

/**
 * 未发现对应的业务类型异常
 * */
public class BtpriceNotFindException extends TPKRuntimeException{
    public BtpriceNotFindException() {
        super();
    }

    public BtpriceNotFindException(String message) {
        super(message);
    }

    public BtpriceNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public BtpriceNotFindException(Throwable cause) {
        super(cause);
    }

    protected BtpriceNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
