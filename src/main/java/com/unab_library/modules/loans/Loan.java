package com.unab_library.modules.loans;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.common.exception.general.UnauthorizedException;
import com.unab_library.core.AbstractRepository;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.users.Person;
import com.unab_library.modules.users.Roles;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserRepository;

public class Loan implements LoanInterface {
    private Loan() {
        // This constructor is private to prevent the creation of a Loan object without
        this.loanDate = new Date();
    }

    public Book getBook() {
        return book;
    }

    private void setBookByIsbn(String isbn) {
        this.book = validateBookByIsbn(isbn);
    }

    public User getUser() {
        return user;
    }

    private void setUserByIdentityDocument(String identityDocument) {
        this.user = validateUserByIdentityDocument(identityDocument);
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    private void setReturnDate(Date returnDate) {
        this.returnDate = validateReturnDate(returnDate);
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

    //#region Validation
    public Book validateBookByIsbn(String isbn) {
        // get the book with the provided ISBN
        boolean checkBookExists = bookRepository.existsByIsbn(isbn);
        if (!checkBookExists) {
            throw NotFoundException.bookNotFound(isbn);
        }
        AbstractRepository.Result<Book> result = bookRepository.getByIsbn(isbn);
        Book bookReturned = result.getValue();
        // check physical stock of the book
        if (bookReturned.getAvailableStock() > 0) {
            throw BadRequestException.bookNotAvailable();
        }

        return bookReturned;
    }
    public User validateUserByIdentityDocument(String identityDocument) {
        // check if the user exists
        boolean checkUserExists = userRepository.existsByIdentityDocument(identityDocument);
        if (!checkUserExists) {
            throw NotFoundException.userNotFound(identityDocument);
        }
        AbstractRepository.Result<User> result = userRepository.getByIdentityDocument(identityDocument);
        User userReturned = result.getValue();
        // check if the user has a loan already
        if (userReturned.hasActiveLoan()) {
            throw BadRequestException.userAlreadyHasLoan();
        }

        return userReturned;
    }
    public Date validateReturnDate(Date returnDate) {
        if (returnDate.before(loanDate)) {
            throw BadRequestException.invalidReturnDate();
        }
        
        // Max return days are 10 days after the loan date for students
        int maxReturnDays = 10;
        // sum days to the loan date
        if (user.getRole().equals(Roles.TEACHER)) {
            // Max return days are 20 days after the loan date
            maxReturnDays = 20;
        } else if (!user.getRole().equals(Roles.STUDENT)) {
            throw UnauthorizedException.unauthorized();
        }

        Date maxReturnDate = new Date(loanDate.getTime() + (maxReturnDays * 24 * 60 * 60 * 1000));
        if (returnDate.after(maxReturnDate)) {
            throw BadRequestException.invalidReturnDate();
        }

        return returnDate;
    }
    //#endregion
    public static LoanBuilder builder() {
        return new LoanBuilder();
    }

    private static final Logger LOGGER = Logger.getLogger(Loan.class.getName());
    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();

    private Book book;
    private User user;
    private Date loanDate;
    private Date returnDate;

    public static class LoanBuilder {
        private LoanBuilder() {
            loan = new Loan();
        }

        public LoanBuilder setBookByIsbn(String isbn) {
            loan.setBookByIsbn(isbn);
            return this;
        }

        public LoanBuilder setUserByIdentityDocument(String identityDocument) {
            loan.setUserByIdentityDocument(identityDocument);
            return this;
        }

        public LoanBuilder setReturnDate(Date returnDate) {
            loan.setReturnDate(returnDate);
            return this;
        }

        public Loan build() {
            return loan;
        }

        private Loan loan;
    }
}
