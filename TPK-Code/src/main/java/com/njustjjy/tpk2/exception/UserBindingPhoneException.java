package com.njustjjy.tpk2.exception;

public class UserBindingPhoneException extends TPKRuntimeException{
    public UserBindingPhoneException() {
        super();
    }

    public UserBindingPhoneException(String message) {
        super(message);
    }

    public UserBindingPhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBindingPhoneException(Throwable cause) {
        super(cause);
    }

    protected UserBindingPhoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
