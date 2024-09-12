package com.sample.expensetracker.advice.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class HttpServerException {
    private long timestamp;
    private String errorType;
    private String errorCode;
    private String message;
    private Map<String, Object> errorParam;
}