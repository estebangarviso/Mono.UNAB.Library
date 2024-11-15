package com.unab_library.modules.book_management;

import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.User;
import java.util.Date;

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
     * Validates the return date of a book loan.
     *
     * @param loanDate the loan date to compare with
     * @return the return date
     */
    public void validateReturnDate(Date loanDate, Date returnDate);
}
