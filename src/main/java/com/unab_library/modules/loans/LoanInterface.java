package com.unab_library.modules.loans;

import java.util.Date;

import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.User;

public interface LoanInterface {
    /**
     * Validates a book by its ISBN.
     *
     * @param isbn the ISBN of the book to validate
     * @return the book with the provided ISBN
     */
    public Book validateBookByIsbn(String isbn);

    /**
     * Validates a user by its identity document.
     *
     * @param identityDocument the identity document of the user to validate
     * @return the user with the provided identity document
     */
    public User validateUserByIdentityDocument(String identityDocument);
    
    /**
     * Validates a return date.
     *
     * @param returnDate the return date to validate
     * @return the return date
     */
    public Date validateReturnDate(Date returnDate);
}