package com.unab_library.core;

import java.util.Date;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.User;

public interface BookManagementInterface {
    /**
     * Get a book by its ISBN.
     *
     * @param isbn the ISBN of the book to validate
     * @return the book with the provided ISBN
     */
    public Book getExistingBook(String isbn);
        /**
     * Get an user by its identity document.
     *
     * @param identityDocument the identity document of the user to validate
     * @return the user with the provided identity document
     */
    public User getValidUserByIdentityDocument(String identityDocument);
    
    /**
     * Validates a return date.
     *
     * @param loanDate the loan date to compare with
     * @return the return date
     */
    public Date getValidReturnDate(Date loanDate);
}
