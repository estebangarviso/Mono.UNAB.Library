package com.unab_library.common.exception.general;

import com.unab_library.common.exception.base.AppException;
import com.unab_library.common.exception.base.AppExceptionCode;

public class UnauthorizedException extends AppException {

    public UnauthorizedException(AppExceptionCode code) {
        super(code);
    }

    public UnauthorizedException(AppExceptionCode code, Throwable cause) {
        super(code, cause);
    }

    // Books
    public static UnauthorizedException unauthorized() {
        return new UnauthorizedException(AppExceptionCode.UNAUTHORIZED, new Throwable("Unauthorized"));
    }
    
}
