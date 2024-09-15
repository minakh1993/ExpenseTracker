package com.sample.expensetracker.exception;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
public class DuplicateUsernameException extends ExpenseTrackerException {
    public DuplicateUsernameException(String message) {
        super(message);
    }

    public DuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}