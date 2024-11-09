package com.unab_library.modules.loans;

import java.util.Date;

public class LoanSaveDTO {
    String isbn;
    String identityString;
    Date returnDate;

    public LoanSaveDTO(String isbn, String identityString, Date returnDate) {
        this.isbn = isbn;
        this.identityString = identityString;
        this.returnDate = returnDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIdentityString() {
        return identityString;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
