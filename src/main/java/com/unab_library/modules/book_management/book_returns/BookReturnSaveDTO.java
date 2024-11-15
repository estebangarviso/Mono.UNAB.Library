package com.unab_library.modules.book_management.book_returns;

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
