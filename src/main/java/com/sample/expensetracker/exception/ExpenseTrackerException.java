package com.sample.expensetracker.exception;

import org.springframework.http.HttpStatus;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
public class ExpenseTrackerException extends Exception {
    public ExpenseTrackerException(String message) {
        super(message);
    }

    public ExpenseTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST ;
    }
}