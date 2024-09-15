package com.sample.expensetracker.exception;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
public class EntityAlreadyExistException extends ExpenseTrackerException{
    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}