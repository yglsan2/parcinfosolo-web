package com.parfinfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    
    public ResourceConflictException(String message) {
        super(message);
    }
    
    public ResourceConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s avec %s '%s' existe déjà", resourceName, fieldName, fieldValue));
    }
} 