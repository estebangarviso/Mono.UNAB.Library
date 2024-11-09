package com.unab_library.common.exception.base;

public class AppException extends RuntimeException {
    private final AppExceptionCode code;

    public AppException(AppExceptionCode code) {
        this.code = code;
    }

    protected AppException(AppExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    protected AppExceptionCode getCode() {
        return code;
    }
}
