package com.sample.expensetracker.advice;

import com.sample.expensetracker.advice.dto.IgnorePropertiesException;
import com.sample.expensetracker.advice.dto.HttpServerException;
import com.sample.expensetracker.exception.ExpenseTrackerException;
import com.sample.expensetracker.exception.ExpenseTrackerRuntimeException;
import com.sample.expensetracker.exception.ServiceException;
import com.sample.expensetracker.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ExpenseTrackerException.class)
    public ResponseEntity<HttpServerException> handleException(ExpenseTrackerException exception) {
        Map<String, Object> exceptionParams = JsonUtil.convertToExceptionParam(exception, IgnorePropertiesException.class);
        HttpServerException httpServerException = extractHttpServerException(exception, exceptionParams);
        return new ResponseEntity<>(httpServerException, exception.getHttpStatus());
    }

    @ExceptionHandler(ExpenseTrackerRuntimeException.class)
    public ResponseEntity<HttpServerException> handleRuntimeException(
            ExpenseTrackerRuntimeException exception) {
        Map<String, Object> exceptionParams = JsonUtil.convertToExceptionParam(exception, IgnorePropertiesException.class);
        HttpServerException httpServerException = extractHttpServerException(exception, exceptionParams);
        return new ResponseEntity<>(httpServerException, exception.getHttpStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<HttpServerException> handleThrowable(Throwable throwable) {
        log.error("Exception: " + throwable.toString() + "\n", throwable);
        ServiceException serviceException = new ServiceException(
                throwable.getClass().getName() + ":" + throwable.getMessage());
        HttpServerException httpServerException = extractHttpServerException(serviceException, new HashMap<>());
        return new ResponseEntity<>(httpServerException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ResponseEntity<HttpServerException> handleAuthenticationException(Exception exception) {
        HttpServerException httpServerException = extractHttpServerException(exception, new HashMap<>());
        return new ResponseEntity<>(httpServerException, HttpStatus.UNAUTHORIZED);
    }

    private HttpServerException extractHttpServerException(
            Throwable throwable, Map<String, Object> exceptionParams) {
        HttpServerException httpServerException = new HttpServerException();
        httpServerException.setMessage(throwable.getMessage());
        httpServerException.setTimestamp(new Date().getTime());
        httpServerException.setErrorParam(exceptionParams);
        httpServerException.setErrorCode(throwable.getClass().getSimpleName());
        httpServerException.setErrorType(throwable.getClass().getSuperclass().getSimpleName());
        return httpServerException;
    }
}
