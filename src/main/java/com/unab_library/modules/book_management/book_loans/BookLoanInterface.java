package com.unab_library.modules.book_management.book_loans;

import java.util.Date;

public interface BookLoanInterface {
    /**
     * Validates the return date of a book loan.
     *
     * @param loanDate the loan date to compare with
     * @return the return date
     */
    public void validateReturnDate(Date loanDate, Date returnDate);
}
