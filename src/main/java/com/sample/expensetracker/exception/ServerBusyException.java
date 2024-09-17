package com.sample.expensetracker.exception;

/**
 * @author M.khoshnevisan
 * @since 9/17/2024
 */
public class ServerBusyException extends ExpenseTrackerRuntimeException {
    public ServerBusyException(String message) {
        super(message);
    }

    public ServerBusyException(String message, Throwable cause) {
        super(message, cause);
    }
}
