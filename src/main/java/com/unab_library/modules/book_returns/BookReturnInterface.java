package com.unab_library.modules.book_returns;

public interface BookReturnInterface {
    /**
     * Validates if the user of the book return is the same as the user of the loan.
     */
    public boolean validateUserBookReturn();
}
