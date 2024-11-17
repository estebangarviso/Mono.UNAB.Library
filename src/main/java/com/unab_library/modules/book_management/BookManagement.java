package com.unab_library.modules.book_management;

import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.exception.general.NotFoundException;
import com.unab_library.common.exception.general.UnauthorizedException;
import com.unab_library.core.AbstractRepository;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.users.Role;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserRepository;
import java.util.Date;
import java.util.UUID;

public class BookManagement implements BookManagementInterface {

    @Override
    public Book getExistingBook(String isbn) {
        // get the book with the provided ISBN
        boolean checkBookExists = bookRepository.existsByIsbn(isbn);
        if (!checkBookExists) {
            throw NotFoundException.bookNotFound(isbn);
        }
        AbstractRepository.Result<Book> result = bookRepository.getByIsbn(isbn);
        return result.getValue();
    }

    @Override
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

    @Override
    public void validateReturnDate(Date loanDate, Date returnDate) {
        if (returnDate.before(loanDate)) {
            throw BadRequestException.invalidReturnDate("Return date must be after the loan date");
        }
        User user = getUser();
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
    }

    protected String getId() {
        return id;
    }

    protected void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public Book getBook() {
        return bookRepository.getByIsbn(isbn).getValue();
    }

    protected void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public User getUser() {
        return userRepository.getByIdentityDocument(identityDocument).getValue();
    }

    protected void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private String id;
    private String isbn;
    private String identityDocument;
    private Date returnDate;
}
