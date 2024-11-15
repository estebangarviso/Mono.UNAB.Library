package com.unab_library.modules.book_management.book_returns;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.modules.book_management.BookManagement;
import com.unab_library.modules.book_management.book_loans.BookLoan;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.User;
import java.util.Date;




public class BookReturn extends BookManagement implements BookReturnInterface {
    private BookReturn() {
        // This constructor is private to prevent the creation of a Return object without
        this.setReturnDate(new Date());
    }

    private int getDaysOverdue() {
        // calculate the days overdue
        long diff = getReturnDate().getTime() - bookLoan.getLoanDate().getTime();
        if (diff < 0) {
            return 0;
        }
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public int getFine() {
        return getDaysOverdue() * FINE_PER_DAY;
    }

    @Override
    public boolean validateUserBookReturn() {
        User user = getUser();
        BookLoan loan = user.getLoan();
        Book loanedBook = loan.getBook();
        return loanedBook.getIsbn().equals(getBook().getIsbn());
    }

    public static BookReturnBuilder builder() {
        return new BookReturnBuilder();
    }

    private static final int FINE_PER_DAY = 1000;
    private final BookReturnRepository bookReturnRepository = BookReturnRepository.getInstance();
    private BookLoan bookLoan;

    public static class BookReturnBuilder {
        private BookReturn bookReturn;

        public BookReturnBuilder() {
            this.bookReturn = new BookReturn();
        }

        public BookReturnBuilder setBookByIsbn(String isbn) {
            // check if the book exists
            this.bookReturn.setBookByIsbn(isbn);
            // check if the book is the one that the user has loaned
            if (!this.bookReturn.validateUserBookReturn()) {
                throw BadRequestException.bookReturnBookMismatch();
            }
            return this;
        }

        public BookReturnBuilder setUserByIdentityDocument(String identityDocument) {
            this.bookReturn.setUserByIdentityDocument(identityDocument);
            return this;
        }

        public BookReturn build() {
            // release the loan of the user when the book is returned
            this.bookReturn.getUser().releaseLoan();
            // increase available stock of the book
            this.bookReturn.getBook().increaseAvailableStock();
            return this.bookReturn;
        }
    }
}
