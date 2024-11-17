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
    public static BadRequestException invalidBookIsbn(String isbn) {
        return new BadRequestException(AppExceptionCode.INVALID_BOOK_ISBN, new Throwable("Invalid book ISBN " + isbn));
    }
    public static BadRequestException invalidBookIsbn(String isbn, Throwable cause) {
        return new BadRequestException(AppExceptionCode.INVALID_BOOK_ISBN, new Throwable("Invalid book ISBN " + isbn, cause));
    }
    public static BadRequestException invalidBookCoverPath() {
        return new BadRequestException(AppExceptionCode.INVALID_BOOK_COVER, new Throwable("Invalid book cover path"));
    }
    public static BadRequestException bookExists(String isbn) {
        return new BadRequestException(AppExceptionCode.BOOK_EXISTS, new Throwable("Book with ISBN " + isbn + " already exists"));
    }

    public static BadRequestException invalidInventoryStock(String message) {
        return new BadRequestException(AppExceptionCode.INVALID_INVENTORY_STOCK, new Throwable(message));
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

    public static BadRequestException invalidUserData(String message) {
        return new BadRequestException(AppExceptionCode.INVALID_USER_DATA, new Throwable(message));
    }

    public static BadRequestException invalidIdentityDocument(String identityDocument) {
        return new BadRequestException(AppExceptionCode.INVALID_IDENTITY_DOCUMENT, new Throwable("Invalid identity document " + identityDocument));
    }

    public static BadRequestException invalidReturnDate(String message) {
        return new BadRequestException(AppExceptionCode.INVALID_RETURN_DATE, new Throwable(message));
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

    public static BadRequestException bookReturnBookMismatch() {
        return new BadRequestException(AppExceptionCode.BOOK_RETURN_BOOK_MISMATCH, new Throwable("Book loan and return books do not match"));
    }

    public static BadRequestException invalidTeacherData(String message) {
        return new BadRequestException(AppExceptionCode.INVALID_TEACHER_DATA, new Throwable(message));
    }

    public static BadRequestException currentCareerIsRequired() {
        return new BadRequestException(AppExceptionCode.CURRENT_CAREER_IS_REQUIRED, new Throwable("Current career is required."));
    }

    public static BadRequestException identityDocumentIsRequired() {
        return new BadRequestException(AppExceptionCode.IDENTITY_DOCUMENT_IS_REQUIRED, new Throwable("Identity document is required."));
    }

    public static BadRequestException genderIsRequired() {
        return new BadRequestException(AppExceptionCode.GENDER_IS_REQUIRED, new Throwable("Gender is required."));
    }

    public static BadRequestException fullNameIsRequired() {
        return new BadRequestException(AppExceptionCode.FULL_NAME_IS_REQUIRED, new Throwable("Full name is required."));
    }

    public static BadRequestException userNotSet() {
        return new BadRequestException(AppExceptionCode.USER_NOT_SET, new Throwable("User is not set"));
    }

    public static BadRequestException isRequired(String message) {
        return new BadRequestException(AppExceptionCode.IS_REQUIRED, new Throwable(message));
    }
}