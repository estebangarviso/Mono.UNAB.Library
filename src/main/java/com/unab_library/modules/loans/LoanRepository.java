package com.unab_library.modules.loans;

import java.util.concurrent.atomic.AtomicReference;

import com.unab_library.core.AbstractRepository;

public class LoanRepository extends AbstractRepository<Loan> {
    // private constructor to prevent instantiation
    private LoanRepository() {}

    private static final AtomicReference<LoanRepository> INSTANCE = new AtomicReference<>();

    public static LoanRepository getInstance() {
        if (INSTANCE.get() == null) {
            INSTANCE.set(new LoanRepository());
        }
        return INSTANCE.get();
    }

    public void save(LoanSaveDTO loanSaveDTO) {
        // build loan
        Loan newLoan = Loan.builder()
            .setBookByIsbn(loanSaveDTO.getIsbn())
            .setUserByIdentityDocument(loanSaveDTO.getIdentityString())
            .setReturnDate(loanSaveDTO.getReturnDate())
            .build();
        // stock business logic
        newLoan.getBook().decreaseAvailableStock();
        // save the loan
        super.save(newLoan);
    }
}
