package com.unab_library.modules.book_loans;

import java.util.Date;

public record BookLoanSaveDTO(
    /**
     * ISBN of the book to loan.
     */
    String isbn,
    /**
     * Identity document of the user is loaning the book.
     */
    String identityDocument,
    /**
     * Planned return date that the user will return the book.
     */
    Date returnDate
) {}