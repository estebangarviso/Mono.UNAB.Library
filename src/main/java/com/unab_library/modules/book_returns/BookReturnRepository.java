package com.unab_library.modules.book_returns;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.reflect.TypeToken;
import com.unab_library.core.AbstractRepository;
import com.unab_library.modules.books.Book;

public class BookReturnRepository extends AbstractRepository<BookReturn> {
    // private constructor to prevent instantiation
    private BookReturnRepository() {
        super("returns.json", new TypeToken<ArrayList<BookReturn>>(){});
    }

    private static final BookReturnRepository INSTANCE = new BookReturnRepository();

    public static BookReturnRepository getInstance() {
        return INSTANCE;
    }

    public void save(BookReturnSaveDTO returnSaveDTO) {
        // build return
        BookReturn newReturn = BookReturn.builder()
            .setBookByIsbn(returnSaveDTO.isbn())
            .setUserByIdentityDocument(returnSaveDTO.identityDocument())
            .build();
        // stock business logic
        newReturn.getBook().increaseAvailableStock();
        // save the return
        super.save(newReturn);
    }

    public void delete(BookReturn returnToDelete) {
        // stock business logic
        Book bookToDelete = returnToDelete.getBook()
        bookToDelete.decreaseInventoryStock();
        // delete the return
        super.delete(returnToDelete);
    }
}
