package com.unab_library;

import org.apache.logging.log4j.Logger;

import com.unab_library.common.libs.LoggerFactory;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookRepository;
import com.unab_library.modules.books.BookSaveDTO;
import com.unab_library.core.AbstractRepository.Result;

public class App {
    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        // BookSaveDTO bookSaveDTO = new BookSaveDTO(
        //     "978-3-16-148410-0",
        //     "The Lord of the Rings - The Fellowship of the Ring",
        //     "J.R.R. Tolkien",
        //     10,
        //     10,
        //     "images/lotr1.jpg"
        // );
        // bookRepository.save(bookSaveDTO);

        Result<Book> result = bookRepository.getByIsbn("978-3-16-148410-0");
        if (result.isSuccess()) {
            Book book = result.getValue();
            logger.info(book.toString());
        } else {
            logger.error(result.getError());
        }
    }
}
