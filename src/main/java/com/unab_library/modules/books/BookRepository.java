package com.unab_library.modules.books;

import com.google.gson.reflect.TypeToken;
import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.core.AbstractRepository;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class BookRepository extends AbstractRepository<Book> {

    private BookRepository() {
        super("books.json", new TypeToken<ArrayList<Book>>(){});
    }
    
    public Result<Book> save(BookSaveDTO bookSaveDTO) {
        Book newBook = Book.builder()
            .setTitle(bookSaveDTO.title())
            .setAuthor(bookSaveDTO.author())
            .setIsbn(bookSaveDTO.isbn())
            .setInventoryStock(bookSaveDTO.inventoryStock())
            .setAvailableStock(bookSaveDTO.availableStock())
            .setCover(bookSaveDTO.coverPath())
            .build();
        return this.save(newBook);
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

    public void updateAvailableStock(String isbn, int quantity) {
        // get the book with the provided ISBN
        Result<Book> result = getByIsbn(isbn);
        // validate: ISBN exists
        if (!result.isSuccess()) {
            throw NotFoundException.bookNotFound(isbn);
        }
        // decrement the available stock of the book with the provided ISBN
        Book book = result.getValue();
        book.setAvailableStock(quantity);
        this.update(result.getIndex(), book);
    }

    private static final AtomicReference<BookRepository> INSTANCE = new AtomicReference<>();

    public static BookRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new BookRepository());
        }
        return INSTANCE.get();
    }
}
