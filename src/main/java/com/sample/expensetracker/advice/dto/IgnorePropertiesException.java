package com.sample.expensetracker.advice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "message", "localizedMessage", "httpStatus", "exceptionType",
        "extraParams", "errorCode", "errorType"})
public abstract class IgnorePropertiesException {
}