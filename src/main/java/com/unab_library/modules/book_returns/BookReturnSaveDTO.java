package com.unab_library.modules.book_returns;

import com.unab_library.modules.book_loans.BookLoan;

public record BookReturnSaveDTO(
    /**
     * ISBN of the book to return.
     */
    String isbn,
    /**
     * Identity document of the user who returns the book.
     */
    String identityDocument
) {}
