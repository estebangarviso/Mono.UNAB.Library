package com.unab_library.modules.books;

import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.core.AbstractRepository;
import java.util.concurrent.atomic.AtomicReference;

public class BookRepository extends AbstractRepository<Book> {

    public void save(BookSaveDTO bookDTO) {
        Book newBook = Book.builder()
            .setTitle(bookDTO.getTitle())
            .setAuthor(bookDTO.getAuthor())
            .setIsbn(bookDTO.getIsbn())
            .setInventoryStock(bookDTO.getInventoryStock())
            .setAvailableStock(bookDTO.getAvailableStock())
            .setCover(bookDTO.getCoverPath())
            .build();
        this.save(newBook);
    }

    public void deleteByIsbn(String isbn) {
        // get the book with the provided ISBN
        Result<Book> result = getByIsbn(isbn);
        // validate: ISBN exists
        if (!result.isSuccess()) {
            throw NotFoundException.bookNotFound(isbn);
        }
        // delete the book with the provided ISBN
        this.delete(result.getIndex());
    }

    public Result<Book> getByIsbn(String isbn) {
        // search for a book with the provided ISBN
        return getByProperty(Book::getIsbn, isbn);
    }

    public boolean existsByIsbn(String isbn) {
        for (Book book : this.getAll()) {
            if (book.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    // private constructor to prevent instantiation
    private BookRepository() {}

    private static final AtomicReference<BookRepository> INSTANCE = new AtomicReference<>();

    public static BookRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new BookRepository());
        }
        return INSTANCE.get();
    }
}
