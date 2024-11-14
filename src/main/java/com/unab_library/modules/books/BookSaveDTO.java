package com.unab_library.modules.books;

public record BookSaveDTO(
    /**
     * ISBN of the book to save.
     */
    String isbn,
    /**
     * Title of the book to save.
     */
    String title,
    /**
     * Author of the book to save.
     */
    String author,
    /**
     * Inventory stock of the book to save.
     */
    int inventoryStock,
    /**
     * Available stock of the book to save.
     */
    int availableStock,
    /**
     * Cover path of the book to save.
     */
    String coverPath
) {}
