package com.unab_library.common.exception.base;

public enum AppExceptionCode {

    // Bad Request
    INVALID_BOOK_COVER(1001, 400, "error.invalid.book.cover"),
    BOOK_EXISTS(1002, 400, "error.book.exists"),
    BOOK_ALREADY_HELD(1007, 400, "error.book.already.held"),
    USER_ALREADY_HAS_LOAN(1008, 400, "error.user.already.has.loan"),
    INVALID_PHYSICAL_STOCK(1003, 400, "error.invalid.physical.stock"),
    INVALID_AVAILABLE_STOCK(1004, 400, "error.invalid.available.stock"),
    INVALID_ROLE(1005, 400, "error.invalid.role"),
    INVALID_RETURN_DATE(1006, 400, "error.invalid.return.date"),
    BOOK_NOT_AVAILABLE(1009, 400, "error.book.not.available"),
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