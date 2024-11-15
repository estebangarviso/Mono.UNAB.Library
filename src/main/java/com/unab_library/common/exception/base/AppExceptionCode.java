package com.unab_library.common.exception.base;

public enum AppExceptionCode {

    // Bad Request
    INVALID_BOOK_COVER(1001, 400, "error.invalid.book.cover"),
    BOOK_EXISTS(1002, 400, "error.book.exists"),
    BOOK_ALREADY_HELD(1007, 400, "error.book.already.held"),
    USER_ALREADY_HAS_LOAN(1008, 400, "error.user.already.has.loan"),
    INVALID_INVENTORY_STOCK(1003, 400, "error.invalid.inventory.stock"),
    INVALID_AVAILABLE_STOCK(1004, 400, "error.invalid.available.stock"),
    INVALID_ROLE(1005, 400, "error.invalid.role"),
    INVALID_RETURN_DATE(1006, 400, "error.invalid.return.date"),
    BOOK_NOT_AVAILABLE(1009, 400, "error.book.not.available"),
    BOOK_RETURN_BOOK_MISMATCH(1010, 400, "error.book.return.book.mismatch"),
    INVALID_TEACHER_DATA(1011, 400, "error.invalid.teacher.data"),
    CURRENT_CAREER_IS_REQUIRED(1012, 400, "error.current.career.cannot.be.null"),
    IDENTITY_DOCUMENT_IS_REQUIRED(1013, 400, "error.identity.document.cannot.be.null"),
    FULL_NAME_IS_REQUIRED(1014, 400, "error.full.name.cannot.be.null"),
    GENDER_IS_REQUIRED(1015, 400, "error.gender.cannot.be.null"),
    INVALID_USER_DATA(1016, 400, "error.invalid.user.data"),
    INVALID_BOOK_ISBN(1017, 400, "error.invalid.book.isbn"),
    INVALID_IDENTITY_DOCUMENT(1018, 400, "error.invalid.identity.document"),
    // Not Found
    BOOK_NOT_FOUND(2001, 404, "error.book.not.found"),
    USER_NOT_FOUND(2002, 404, "error.user.not.found"),
    // Internal Server Error
    LOADING_BOOK_COVER(3001, 500, "error.loading.book.cover"),
    // Unauthorized
    UNAUTHORIZED(4001, 401, "error.unauthorized");

    AppExceptionCode(Integer codeApp, Integer statusCode, String messageKey) {
        this.internalCode = codeApp;
        this.statusCode = statusCode;
        this.messageKey = messageKey;
    }

    private final Integer internalCode;

    private final Integer statusCode;

    private final String messageKey;

    public Integer getInternalCode() {
        return internalCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}