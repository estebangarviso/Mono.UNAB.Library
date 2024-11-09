package com.unab_library.common.exception.general;

import com.unab_library.common.exception.base.AppException;
import com.unab_library.common.exception.base.AppExceptionCode;

public class InternalServerErrorException extends AppException {

    public InternalServerErrorException(AppExceptionCode code) {
        super(code);
    }

    public InternalServerErrorException(AppExceptionCode code, Throwable cause) {
        super(code, cause);
    }

    // Books
    public static InternalServerErrorException errorLoadingBookCover(Throwable cause) {
        return new InternalServerErrorException(AppExceptionCode.LOADING_BOOK_COVER, new Throwable("Something went wrong while loading the book cover", cause));
    }
    
}
