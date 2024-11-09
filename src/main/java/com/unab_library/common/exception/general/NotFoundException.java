package com.unab_library.common.exception.general;

import com.unab_library.common.exception.base.AppException;
import com.unab_library.common.exception.base.AppExceptionCode;

public class NotFoundException extends AppException {

    public NotFoundException(AppExceptionCode code) {
        super(code);
    }

    public NotFoundException(AppExceptionCode code, Throwable cause) {
        super(code, cause);
    }

    // Books
    public static NotFoundException bookNotFound(String isbn) {
        return new NotFoundException(AppExceptionCode.BOOK_NOT_FOUND, new Throwable("Book with ISBN " + isbn + " not found"));
    }

    public static NotFoundException userNotFound(String identityDocument) {
        return new NotFoundException(AppExceptionCode.USER_NOT_FOUND, new Throwable("User with identity document " + identityDocument + " not found"));
    }

}