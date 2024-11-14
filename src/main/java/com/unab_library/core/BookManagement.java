package com.unab_library.core;

import java.util.Date;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.common.exception.general.UnauthorizedException;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.users.Role;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserRepository;

public class BookManagement implements BookManagementInterface {

    public Book getExistingBook(String isbn) {
        // get the book with the provided ISBN
        boolean checkBookExists = bookRepository.existsByIsbn(isbn);
        if (!checkBookExists) {
            throw NotFoundException.bookNotFound(isbn);
        }
        AbstractRepository.Result<Book> result = bookRepository.getByIsbn(isbn);
        return result.getValue();
    }

    public User getValidUserByIdentityDocument(String identityDocument) {
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

    public Date getValidReturnDate(Date loanDate) {
        if (returnDate.before(loanDate)) {
            throw BadRequestException.invalidReturnDate("Return date must be after the loan date");
        }
        
        // Max return days are 10 days after the loan date for students
        int maxReturnDays = 10;
        // sum days to the loan date
        if (user.getRole().equals(Role.TEACHER)) {
            // Max return days are 20 days after the loan date
            maxReturnDays = 20;
        } else if (!user.getRole().equals(Role.STUDENT)) {
            throw UnauthorizedException.unauthorized();
        }

        Date maxReturnDate = new Date(loanDate.getTime() + (maxReturnDays * 24 * 60 * 60 * 1000));
        if (returnDate.after(maxReturnDate)) {
            throw BadRequestException.invalidReturnDate("Return date must be before " + maxReturnDate);
        }

        return returnDate;
    }

    public Book getBook() {
        return book;
    }

    private void setBookByIsbn(String isbn) {
        Book existingBook = getExistingBook(isbn);
        // check if the book is available
        if (existingBook.getAvailableStock() == 0) {
            throw BadRequestException.bookNotAvailable();
        }
        this.book = existingBook;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    private void setUserByIdentityDocument(String identityDocument) {
        this.user = getValidUserByIdentityDocument(identityDocument);
    }

    public Date getReturnDate() {
        return returnDate;
    }

    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private Book book;
    private User user;
    private Date returnDate;
}
