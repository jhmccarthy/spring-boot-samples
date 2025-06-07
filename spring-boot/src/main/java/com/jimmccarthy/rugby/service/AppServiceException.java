package com.jimmccarthy.rugby.service;

import java.io.Serial;

public class AppServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5251597259918945182L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message the detail message
     */
    public AppServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public AppServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
