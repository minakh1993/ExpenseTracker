package com.sample.expensetracker.exception;


import org.springframework.http.HttpStatus;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
public class ExpenseTrackerRuntimeException extends RuntimeException {

    public ExpenseTrackerRuntimeException(String message) {
        super(message);
    }

    public ExpenseTrackerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR ;
    }
}