package com.unab_library.modules.books;

import java.util.Arrays;
import java.util.List;
import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.exception.general.InternalServerErrorException;
import com.unab_library.common.libs.MediaUtils;
import com.unab_library.modules.books.Book.BookBuilder;

public class Book implements BookInterface {
    private Book() {}

    public String getIsbn() {
        return isbn;
    }

    public String setIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw BadRequestException.bookExists(isbn);
        }
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

    public String setCover(String coverPath) {
        if (!isValidCover(coverPath)) {
            throw BadRequestException.invalidBookCoverPath();
        }
        this.coverPath = coverPath;
        return this.coverPath;
    }

    public byte[] getCoverBytes() {
        try {
            return MediaUtils.loadImage(this.coverPath);
        } catch (Exception e) {
            throw InternalServerErrorException.errorLoadingBookCover(e);
        }
    }

    //#region BookInterface implementation
    public void decreaseInventoryStock() {
        if (this.inventoryStock <= 0) {
            throw BadRequestException.invalidInventoryStock("Inventory stock cannot be less than or equal to 0");
        }
        this.inventoryStock--;
    }
    public void decreaseAvailableStock() {
        if (this.availableStock <= 0) {
            throw BadRequestException.invalidAvailableStock("Available stock cannot be less than 0");
        }
        this.availableStock--;
    }

    public void increaseAvailableStock() {
        if (this.availableStock >= this.inventoryStock) {
            throw BadRequestException.invalidAvailableStock("Available stock cannot exceed physical stock");
        }
        this.availableStock++;
    }
    public boolean isValidCover(String coverPath) {
        boolean isValid = coverPath != null && !coverPath.isEmpty();
        if (isValid) {
            String extension = MediaUtils.getFileExtension(coverPath);
            isValid = MediaUtils.isValidExtension(extension, IMAGE_EXTENSIONS);
        }
        return isValid;
    }

    public Book validateStocks() {
        if (this.inventoryStock <= 0) {
            throw BadRequestException.invalidInventoryStock("Inventory stock cannot be less than or equal to 0");
        }
        if (this.inventoryStock < this.availableStock) {
            throw BadRequestException.invalidInventoryStock("Inventory stock cannot be less than available stock");
        }
        if (this.availableStock <= 0 || this.availableStock > this.inventoryStock) {
            throw BadRequestException.invalidAvailableStock("Available stock cannot be less than 0 or exceed inventory stock");
        }
        return this;
    }
    //#endregion

    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");
    private String isbn;
    private String title;
    private String author;
    private int inventoryStock;
    private int availableStock;
    private String coverPath;

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private Book book;

        private BookBuilder() {
            this.book = new Book();
        }

        public BookBuilder setIsbn(String isbn) {
            this.book.setIsbn(isbn);
            return this;
        }

        public BookBuilder setTitle(String title) {
            this.book.setTitle(title);
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.book.setAuthor(author);
            return this;
        }

        public BookBuilder setInventoryStock(int inventoryStock) {
            this.book.setInventoryStock(inventoryStock);
            return this;
        }

        public BookBuilder setAvailableStock(int availableStock) {
            this.book.setAvailableStock(availableStock);
            return this;
        }

        public BookBuilder setCover(String coverPath) {
            this.book.setCover(coverPath);
            return this;
        }

        public Book build() {
            return this.book.validateStocks();
        }
    }
}
