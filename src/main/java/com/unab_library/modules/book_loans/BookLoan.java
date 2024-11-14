package com.unab_library.modules.book_loans;

import java.util.Calendar;
import java.util.Date;

import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.common.exception.general.UnauthorizedException;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.users.Person;
import com.unab_library.modules.users.Role;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserRepository;
import com.unab_library.core.AbstractRepository;
import com.unab_library.core.BookManagement;


public class BookLoan extends BookManagement {
    private BookLoan() {
        // This constructor is private to prevent the creation of a Loan object without
        this.loanDate = new Date();
    }

    public Date getLoanDate() {
        return loanDate;
    }

    private void setReturnDate(Date returnDate) {
        this.returnDate = getValidReturnDate(returnDate);
    }

    private String getFormattedReturnDate() {
        // Must be in this format: DD-MM-YYYY
        var calendar = Calendar.getInstance();
        calendar.setTime(returnDate);

        return String.format("%02d-%02d-%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
    }

    public String getReceipt() {
        Person borrower = user.getPerson();
        return """
            --------------------------------
            |          Loan Receipt         |
            --------------------------------
            | Author: %s
            | Title: %s
            | Date due: %s
            | Borrower: %s
            --------------------------------
        """.formatted(book.getAuthor(), book.getTitle(), getFormattedReturnDate(), borrower.getFullName());
    }

    public static LoanBuilder builder() {
        return new LoanBuilder();
    }

    private Date loanDate;

    public static class LoanBuilder {
        private LoanBuilder() {
            bookLoan = new BookLoan();
        }

        public LoanBuilder setBookByIsbn(String isbn) {
            bookLoan.setBookByIsbn(isbn);
            return this;
        }

        public LoanBuilder setUserByIdentityDocument(String identityDocument) {
            bookLoan.setUserByIdentityDocument(identityDocument);
            return this;
        }

        public LoanBuilder setReturnDate(Date returnDate) {
            bookLoan.setReturnDate(returnDate);
            return this;
        }

        public BookLoan build() {
            return bookLoan;
        }

        private BookLoan bookLoan;
    }
}
