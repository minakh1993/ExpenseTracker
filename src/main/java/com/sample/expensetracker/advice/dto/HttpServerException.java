package com.sample.expensetracker.advice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
@Schema(name = "HttpServerException")
public class HttpServerException {
    @Schema(description = "${HttpServerException.timestamp}", example = "1653398406549")
    private long timestamp;
    @Schema(description = "${HttpServerException.errorType}", example = "CustomerException")
    private String errorType;
    @Schema(description = "${HttpServerException.errorCode}", example = "CustomerAgeLimitException")
    private String errorCode;
    @Schema(description = "${HttpServerException.message}", example = "customer age limit")
    private String message;
    @Schema(description = "${HttpServerException.errorParam}", implementation = Object.class,
            example = "{\"param1\":\"value1\"}")
    private Map<String, Object> errorParam;
}