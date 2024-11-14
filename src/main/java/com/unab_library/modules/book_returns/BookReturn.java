package com.unab_library.modules.book_returns;

import java.util.Date;

import com.unab_library.core.BookManagement;
import com.unab_library.common.exception.base.AppExceptionCode;
import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.core.BadRequestException;
import com.unab_library.modules.book_loans.BookLoan;
import com.unab_library.modules.book_returns.BookReturn.BookReturnBuilder;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.User;
import com.unab_library.modules.book_loans.BookLoan;

public class BookReturn extends BookManagement implements BookReturnInterface {
    private static final int FINE_PER_DAY = 1000;
    private BookLoan bookLoan;

    private BookReturn() {
        // This constructor is private to prevent the creation of a Return object without
        this.returnDate = new Date();
    }

    private int getDaysOverdue() {
        // calculate the days overdue
        int diff = returnDate.getTime() - bookLoan.getLoanDate().getTime();
        if (diff < 0) {
            return 0;
        }
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public int getFine() {
        return getDaysOverdue() * FINE_PER_DAY;
    }

    public boolean validateUserBookReturn() {
        User user = getUser();
        BookLoan loan = user.getLoan();
        Book loanedBook = loan.getBook();
        return loanedBook.getIsbn().equals(book.getIsbn());
    }

    public static class BookReturnBuilder {
        private BookReturn bookReturn;

        public BookReturnBuilder() {
            this.bookReturn = new BookReturn();
        }

        public BookReturnBuilder setBookByIsbn(String isbn) {
            try {
                // check if the book exists
                this.bookReturn.book = getExistingBook(isbn);
                // check if the book is the one that the user has loaned
                if (!validateUserBookReturn()) {
                    throw BadRequestException.bookReturnBookMismatch();
                }
                return this;
            } catch (Throwable e) {
                throw e;
            }
        }

        public BookReturnBuilder setUserByIdentityDocument(String identityDocument) {
            this.bookReturn.user = getValidUserByIdentityDocument(identityDocument);
            return this;
        }

        public BookReturn build() {
            // enable the user to request a new book loan
            this.bookReturn.getUser().setLoan(null);
            // increase available stock of the book
            this.bookReturn.getBook().increaseAvailableStock();
            return this.bookReturn;
        }
    }
}
