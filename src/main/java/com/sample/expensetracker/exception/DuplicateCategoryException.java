package com.sample.expensetracker.exception;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
public class DuplicateCategoryException extends ExpenseTrackerException{
    public DuplicateCategoryException(String message) {
        super(message);
    }

    public DuplicateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}