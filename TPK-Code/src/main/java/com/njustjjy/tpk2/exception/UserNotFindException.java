package com.njustjjy.tpk2.exception;

/**
 * 用户未查询到
 *
 * */
public class UserNotFindException extends TPKRuntimeException{
    public UserNotFindException() {
        super();
    }

    public UserNotFindException(String message) {
        super(message);
    }

    public UserNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFindException(Throwable cause) {
        super(cause);
    }

    public UserNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
