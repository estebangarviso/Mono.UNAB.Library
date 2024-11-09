package com.unab_library.modules.books;

public interface BookInterface {
    /**
     * Checks if the provided cover image path is valid.
     *
     * @param coverPath the path to the cover image file
     * @return true if the cover image path is valid, false otherwise
     */
    public boolean isValidCover(String coverPath);

    /**
     * Increases the available stock of the book by 1.
     */
    public void increaseAvailableStock();

    /**
     * Decreases the available stock of the book by 1.
     */
    public void decreaseAvailableStock();

    /**
     * Sets the available stock of the book.
     *
     * @param availableStock the available stock of the book
     */
    public int setAvailableStock(int availableStock);

    /**
     * Returns a book and validates its stocks.
     */
    public Book validateStocks();

    /**
     * Sets the inventory stock of the book.
     *
     * @param inventoryStock the inventory stock of the book
     */
    public int setInventoryStock(int inventoryStock);

    /**
     * The total amount of books in the library.
     */
    public int getInventoryStock();
}
