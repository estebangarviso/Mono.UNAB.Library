package com.unab_library.modules.users;

public interface UserInterface {
    /**
     * Validates if the user has an active loan.
     */
    public boolean hasActiveLoan();

    /**
     * Releases the loan of the user.
     */
    public void releaseLoan();

    /**
     * Attaches a loan to the user.
     * @param idLoan the id of the loan to attach
     */
    public void attachLoan(String idLoan);
}