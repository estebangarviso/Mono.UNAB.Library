package com.unab_library.modules.books;

public class BookSaveDTO {
    private String isbn;
    private String title;
    private String author;
    private int inventoryStock;
    private int availableStock;
    private String coverPath;

    public BookSaveDTO(String isbn, String title, String author, int inventoryStock, int availableStock, String coverPath) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.inventoryStock = inventoryStock;
        this.availableStock = availableStock;
        this.coverPath = coverPath;
    }

    public String getIsbn() {
        return isbn;
    }

    public String setIsbn(String isbn) {
        this.isbn = isbn;
        return this.isbn;
    }

    public String getTitle() {
        return title;
    }

    public String setTitle(String title) {
        this.title = title;
        return this.title;
    }

    public String getAuthor() {
        return author;
    }

    public String setAuthor(String author) {
        this.author = author;
        return this.author;
    }

    public int getInventoryStock() {
        return inventoryStock;
    }

    public int setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
        return this.inventoryStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public int setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
        return this.availableStock;
    }

    public String getCoverPath() {
        return coverPath;
    }   

    public String setCoverPath(String coverPath) {
        this.coverPath = coverPath;
        return this.coverPath;
    }
}
