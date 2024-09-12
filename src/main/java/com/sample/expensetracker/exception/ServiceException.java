package com.sample.expensetracker.exception;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
public class ServiceException extends ExpenseTrackerRuntimeException{
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}