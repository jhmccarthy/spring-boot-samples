package com.jimmccarthy.rugby.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This class is used to handle exceptions thrown in a REST controller and send back an error to the client. Multiple
 * {@code ExceptionHandler} methods can be defined to handle different exceptions.
 * <p>
 * One thing to keep in mind here is that the exceptions declared with {@code @ExceptionHandler} with the exception used
 * as the argument of the method. If they don't match, an {@code IllegalStateException} will be thrown at runtime.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Handle a {@code AccessDeniedException} that's thrown when a user fails to authenticate and return access
     * denied error (403).
     *
     * @param e       the exception
     * @param request the web request
     * @return access denied error message
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        log.error("User failed to authenticate due to {}", e.getLocalizedMessage(), e);

        return new ResponseEntity<>("Access denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handle a {@code RuntimeException} that's thrown in a controller and return an internal server error (500).
     *
     * @param e       the exception
     * @param request the web request
     * @return an internal server error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request) {
        log.error("Request failed due to {}", e.getLocalizedMessage(), e);

        return new ResponseEntity<>("The request could not be processed; please try again later",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
