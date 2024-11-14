package com.unab_library.modules.book_loans;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.reflect.TypeToken;
import com.unab_library.core.AbstractRepository;
import com.unab_library.modules.books.Book;

public class BookLoanRepository extends AbstractRepository<BookLoan> {
    // private constructor to prevent instantiation
    private BookLoanRepository() {
        super("loans.json", new TypeToken<ArrayList<BookLoan>>(){});
    }

    private static final AtomicReference<BookLoanRepository> INSTANCE = new AtomicReference<>();

    public static BookLoanRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new BookLoanRepository());
        }
        return INSTANCE.get();
    }

    public void save(BookLoanSaveDTO loanSaveDTO) {
        // build loan
        BookLoan newLoan = BookLoan.builder()
            .setBookByIsbn(loanSaveDTO.isbn())
            .setUserByIdentityDocument(loanSaveDTO.identityDocument())
            .setReturnDate(loanSaveDTO.returnDate())
            .build();
        // stock business logic
        newLoan.getBook().decreaseAvailableStock();
        // save the loan
        super.save(newLoan);
    }
}
