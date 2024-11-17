package com.unab_library.modules.book_management.book_returns;

import com.google.gson.reflect.TypeToken;
import com.unab_library.core.AbstractRepository;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class BookReturnRepository extends AbstractRepository<BookReturn> {
    // private constructor to prevent instantiation
    private BookReturnRepository() {
        super("book_returns.json", new TypeToken<ArrayList<BookReturn>>(){});
    }

    private static final AtomicReference<BookReturnRepository> INSTANCE = new AtomicReference<>();

    public static BookReturnRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new BookReturnRepository());
        }
        return INSTANCE.get();
    }

    public Result<BookReturn> save(BookReturnSaveDTO returnSaveDTO) {
        // build return
        BookReturn newReturn = BookReturn.builder()
            .setIdentityDocument(returnSaveDTO.identityDocument())
            .setIsbn(returnSaveDTO.isbn())
            .build();
        // save the return
        return super.save(newReturn);
    }

    public Result<BookReturn> getById(String id) {
        return getByProperty(BookReturn::getId, id);
    }
}
