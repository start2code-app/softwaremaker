package com.company.softwaremaker.exception;

public class QuickBooksTokenRefreshException extends QuickBooksException {

    public QuickBooksTokenRefreshException(String message) {
        super(message);
    }

    public QuickBooksTokenRefreshException(String message, Throwable cause) {
        super(message, cause);
    }
}
