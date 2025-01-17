package com.unab_library.modules.book_management.book_loans;

import com.google.gson.reflect.TypeToken;
import com.unab_library.core.AbstractRepository;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class BookLoanRepository extends AbstractRepository<BookLoan> {
    // private constructor to prevent instantiation
    private BookLoanRepository() {
        super("book_loans.json", new TypeToken<ArrayList<BookLoan>>(){});
    }

    private static final AtomicReference<BookLoanRepository> INSTANCE = new AtomicReference<>();

    public static BookLoanRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new BookLoanRepository());
        }
        return INSTANCE.get();
    }

    public Result<BookLoan> save(BookLoanSaveDTO loanSaveDTO) {
        // build loan
        BookLoan newLoan = BookLoan.builder()
            .setIsbn(loanSaveDTO.isbn())
            .setIdentityDocument(loanSaveDTO.identityDocument())
            .setReturnDate(loanSaveDTO.returnDate())
            .build();
        // stock business logic
        newLoan.getBook().decreaseAvailableStock();
        // attach the loan to the user
        newLoan.getUser().attachLoan(newLoan.getId());
        // save the loan
        return super.save(newLoan);
    }

    public Result<BookLoan> getById(String idBookLoan) {
        return getByProperty(BookLoan::getId, idBookLoan);
    }
}
