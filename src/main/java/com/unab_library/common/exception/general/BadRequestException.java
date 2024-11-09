package com.unab_library.common.exception.general;

import com.unab_library.common.exception.base.AppException;
import com.unab_library.common.exception.base.AppExceptionCode;

public class BadRequestException extends AppException {

    public BadRequestException(AppExceptionCode code) {
        super(code);
    }

    public BadRequestException(AppExceptionCode code, Throwable cause) {
        super(code, cause);
    }

    // Books
    public static BadRequestException invalidBookCoverPath() {
        return new BadRequestException(AppExceptionCode.INVALID_BOOK_COVER, new Throwable("Invalid book cover path"));
    }
    public static BadRequestException bookExists(String isbn) {
        return new BadRequestException(AppExceptionCode.BOOK_EXISTS, new Throwable("Book with ISBN " + isbn + " already exists"));
    }

    public static BadRequestException invalidInventoryStock() {
        return new BadRequestException(AppExceptionCode.INVALID_PHYSICAL_STOCK, new Throwable("Invalid physical stock"));
    }

    public static BadRequestException invalidAvailableStock() {
        return new BadRequestException(AppExceptionCode.INVALID_AVAILABLE_STOCK, new Throwable("Invalid available stock"));
    }
    public static BadRequestException invalidAvailableStock(String message) {
        return new BadRequestException(AppExceptionCode.INVALID_AVAILABLE_STOCK, new Throwable(message));
    }

    public static BadRequestException invalidRole() {
        return new BadRequestException(AppExceptionCode.INVALID_ROLE, new Throwable("Invalid role"));
    }

    public static BadRequestException invalidReturnDate() {
        return new BadRequestException(AppExceptionCode.INVALID_RETURN_DATE, new Throwable("Invalid return date"));
    }

    public static BadRequestException bookAlreadyHeld() {
        return new BadRequestException(AppExceptionCode.BOOK_ALREADY_HELD, new Throwable("Book is already held"));
    }

    public static BadRequestException userAlreadyHasLoan() {
        return new BadRequestException(AppExceptionCode.USER_ALREADY_HAS_LOAN, new Throwable("User already has a loan"));
    }

    public static BadRequestException bookNotAvailable() {
        return new BadRequestException(AppExceptionCode.BOOK_NOT_AVAILABLE, new Throwable("Book is not available"));
    }

    public static BadRequestException invalidPhysicalStock(String string) {
        return new BadRequestException(AppExceptionCode.INVALID_PHYSICAL_STOCK, new Throwable(string));
    }
}