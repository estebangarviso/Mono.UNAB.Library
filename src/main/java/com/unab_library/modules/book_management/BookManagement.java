package com.unab_library.modules.book_management;

import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserRepository;
import java.util.Date;
import java.util.UUID;

public class BookManagement {

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
