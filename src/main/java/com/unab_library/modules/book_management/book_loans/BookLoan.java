package com.unab_library.modules.book_management.book_loans;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.unab_library.common.exception.general.BadRequestException;
import com.unab_library.common.exception.general.UnauthorizedException;
import com.unab_library.common.libs.MediaUtils;
import com.unab_library.core.AbstractRepository.Result;
import com.unab_library.modules.book_management.BookManagement;
import com.unab_library.modules.books.Book;
import com.unab_library.modules.users.Person;
import com.unab_library.modules.users.Role;
import com.unab_library.modules.users.User;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BookLoan extends BookManagement implements BookLoanInterface {
    private BookLoan() {
        // This constructor is private to prevent the creation of a Loan object without
        this.loanDate = new Date();
    }

    public Date getLoanDate() {
        return loanDate;
    }

    @Override
    public void setReturnDate(Date returnDate) {
        // validate return date
        validateReturnDate(loanDate, returnDate);

        // set return date
        super.setReturnDate(returnDate);
    }

    private String getFormattedReturnDate() {
        // Must be in this format: DD-MM-YYYY
        var calendar = Calendar.getInstance();
        calendar.setTime(getReturnDate());

        return String.format("%02d-%02d-%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
    }

    private String getFormattedLoanDate() {
        // Must be in this format: DD-MM-YYYY
        var calendar = Calendar.getInstance();
        calendar.setTime(loanDate);

        return String.format("%02d-%02d-%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
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

    public byte[] generateReceipt() {
        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            
            document.open();
            
            // Add header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph header = new Paragraph("Loan Receipt", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("\n"));
            
            // Add content
            Person borrower = getUser().getPerson();
            Book loanedBook = getBook();
            
            document.add(new Paragraph("Author: " + loanedBook.getAuthor()));
            document.add(new Paragraph("Title: " + loanedBook.getTitle()));
            document.add(new Paragraph("Date due: " + getFormattedReturnDate()));
            document.add(new Paragraph("Borrower: " + borrower.getFullName()));
            
            document.close();
            return outputStream.toByteArray();
            
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF receipt", e);
        }
    }

    public String getReceiptPath() {
        return MediaUtils.RESOURCES_DIR + "receipts/loan_receipt_" + getBook().getIsbn() + "_" + getUser().getPerson().getIdentityDocument() + ".pdf";
    }

    public static List<BookLoan> getAll() {
        return bookLoanRepository.getAll();
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return String.format("BookLoan[" +
            "book=%s, " +
            "identityDocument='%s', " +
            "loanDate='%s', " +
            "returnDate='%s'" +
            "]", getBook().toString(), getUser().getPerson().getIdentityDocument(), getFormattedLoanDate(), getFormattedReturnDate());
    }

    public static LoanBuilder builder() {
        return new LoanBuilder();
    }

    public static Result<BookLoan> createLoan(BookLoanSaveDTO bookLoanSaveDTO) {
        return bookLoanRepository.save(bookLoanSaveDTO);
    }

    private static final BookLoanRepository bookLoanRepository = BookLoanRepository.getInstance();
    private Date loanDate;

    public static class LoanBuilder {
        private LoanBuilder() {
            bookLoan = new BookLoan();
        }

        public LoanBuilder setIsbn(String isbn) {
            bookLoan.setIsbn(isbn);
            return this;
        }

        public LoanBuilder setIdentityDocument(String identityDocument) {
            bookLoan.setIdentityDocument(identityDocument);
            return this;
        }

        public LoanBuilder setReturnDate(Date returnDate) {
            bookLoan.setReturnDate(returnDate);
            return this;
        }

        public BookLoan build() {
            bookLoan.setId();
            return bookLoan;
        }

        private BookLoan bookLoan;
    }
}
