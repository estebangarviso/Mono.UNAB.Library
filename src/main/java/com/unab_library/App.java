package com.unab_library;

import com.unab_library.common.libs.LoggerFactory;
import com.unab_library.core.AbstractRepository.Result;
import com.unab_library.modules.book_management.book_loans.BookLoan;
import com.unab_library.modules.book_management.book_loans.BookLoanSaveDTO;
import com.unab_library.modules.book_management.book_returns.BookReturn;
import com.unab_library.modules.book_management.book_returns.BookReturnSaveDTO;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.books.BookSaveDTO;
import com.unab_library.modules.users.AcademicDegree;
import com.unab_library.modules.users.AcademicDegreeCategory;
import com.unab_library.modules.users.Gender;
import com.unab_library.modules.users.Person;
import com.unab_library.modules.users.User;
import com.unab_library.modules.users.UserStudentSaveDTO;
import com.unab_library.modules.users.UserTeacherSaveDTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Logger;


public class App {
    private static final String BOOK_ISBN = "978-3-16-148410-0";
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        // Ask the user to run the creation of the entities or return all books
        logger.info("Do you want to run the creation of the entities or return all books? (loan/return/cleanup)");
        String option = System.console().readLine();
        switch (option) {
            case "loan":
                runCreation();
                break;
            case "return":
                returnAllBooks();
                break;
            case "cleanup":
                cleanup();
                break;
            default:
                logger.error("Invalid option");
        }
    }

    public static void runCreation() {
        // Create a book
        createBook();
        // Create a student
        createStudent();
        // Create a teacher
        createTeacher();
        // Create a student's loan
        createStudentLoan();
        // Create a teacher's loan
        createTeachLoan();
        // Save all book loans receipts in PDF format
        saveAllBookLoansReceiptsPDFs();
    }

    public static void returnAllBooks() {
        // Return all books
        List<BookLoan> bookLoans = BookLoan.getAll();
        for (BookLoan loan : bookLoans) {
            BookReturnSaveDTO newReturn = new BookReturnSaveDTO(loan.getBook().getIsbn(), loan.getUser().getPerson().getIdentityDocument());
            Result<BookReturn> result = BookReturn.createBookReturn(newReturn);
            if (result.isSuccess()) {
                logger.info("Book returned successfully\n" + result.getValue().toString());
            } else {
                logger.error("An error occurred while returning the book");
            }
        }
    }

    public static void cleanup() {
        // Delete all files files in /data folder
        logger.info("Cleaning up data folder");
        try {
            ProcessBuilder pb = new ProcessBuilder("rm", "-rf", "data");
            pb.start();
            logger.info("Data folder cleaned up");
            // mkdir data folder
            ProcessBuilder pb2 = new ProcessBuilder("mkdir", "data");
            pb2.start();
            logger.info("Data folder recreated");
        } catch (IOException e) {
            logger.error("An error occurred while cleaning up the data folder", e);
        }
    }

    public static void createBook() {
        BookSaveDTO newBook = new BookSaveDTO(
            BOOK_ISBN,
            "The Lord of the Rings - The Fellowship of the Ring",
            "J.R.R. Tolkien",
            10, 10, "images/lotr1.jpg");
        Result<Book> result = Book.createBook(newBook);
        if (result.isSuccess()) {
            logger.info("Book created successfully\n" + result.getValue().toString());
        } else {
            logger.error("An error occurred while creating the book");
        }
    }

    public static void createStudent() {
        UserStudentSaveDTO newUser = new UserStudentSaveDTO(
            new Person("18380815-K", Gender.MALE, "Esteban Garviso"),
            "Civil Engineering Information Technology",
            "Civil Engineering Information Technology"
        );

        Result<User> result = User.createStudent(newUser);
        if (result.isSuccess()) {
            logger.info("Student created successfully\n" + result.getValue().toString());
        } else {
            logger.error("An error occurred while creating the student");
        }
    }

    public static void createTeacher() {
        UserTeacherSaveDTO newTeacher = new UserTeacherSaveDTO(
            new Person("9030713-4", Gender.MALE, "Ismael Moreno"),
            "Computer Science",
            "University Professor",
            List.of(
                new AcademicDegree("Software Engineering", AcademicDegreeCategory.MASTER),
                new AcademicDegree("Computer Science", AcademicDegreeCategory.DOCTORATE)
            )
        );

        Result<User> result = User.createTeacher(newTeacher);
        if (result.isSuccess()) {
            logger.info("Teacher created successfully\n" + result.getValue().toString());
        } else {
            logger.error("An error occurred while creating the teacher");
        }
    }

    public static void createStudentLoan() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        int loanDays = 10;
        calendar.setTime(now);
        calendar.add(Calendar.DATE, loanDays);
        Date returnDate = calendar.getTime();
        BookLoanSaveDTO bookLoan = new BookLoanSaveDTO(BOOK_ISBN, "18380815-K", returnDate);
        Result<BookLoan> result = BookLoan.createLoan(bookLoan);
        if (result.isSuccess()) {
            logger.info("Book borrowed to student successfully\n" + result.getValue().toString());
        } else {
            logger.error("An error occurred while borrowing the book to the student");
        }
    }

    public static void createTeachLoan() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        int loanDays = 20;
        calendar.setTime(now);
        calendar.add(Calendar.DATE, loanDays);
        Date returnDate = calendar.getTime();
        BookLoanSaveDTO bookLoan = new BookLoanSaveDTO(BOOK_ISBN, "9030713-4", returnDate);
        Result<BookLoan> result = BookLoan.createLoan(bookLoan);
        if (result.isSuccess()) {
            logger.info("Book borrowed to teacher successfully\n" + result.getValue().toString());
        } else {
            logger.error("An error occurred while borrowing the book to the teacher");
        }
    }

    public static void saveAllBookLoansReceiptsPDFs() {
        logger.info("Saving all book loans receipts in PDF format");
        List<BookLoan> bookLoans = BookLoan.getAll();
        // Print the book loans 
        for (BookLoan loan : bookLoans) {
            logger.info("Saving book loan receipt for " + loan.getUser().getPerson().getFullName());
            // Print the receipt
            try {
                byte[] receipt = loan.generateReceipt();
                String receiptPath = loan.getReceiptPath();
                FileOutputStream fos = new FileOutputStream(receiptPath);
                fos.write(receipt);
                fos.close();
                logger.info("Receipt saved at " + receiptPath);
            } catch (IOException e) {
                logger.error("An error occurred while saving the receipt", e);
            }
        }
    }

}
