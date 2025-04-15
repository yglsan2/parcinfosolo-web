package com.parfinfo.exception;

public class CookiePreferencesException extends RuntimeException {
    
    public CookiePreferencesException(String message) {
        super(message);
    }
    
    public CookiePreferencesException(String message, Throwable cause) {
        super(message, cause);
    }
} 