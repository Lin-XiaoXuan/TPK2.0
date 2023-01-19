package com.njustjjy.tpk2.exception;

public class EmpNotFindException extends TPKRuntimeException{
    public EmpNotFindException() {
        super();
    }

    public EmpNotFindException(String message) {
        super(message);
    }

    public EmpNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmpNotFindException(Throwable cause) {
        super(cause);
    }

    protected EmpNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
