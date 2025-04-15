package com.parfinfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImportException extends RuntimeException {
    
    public ImportException(String message) {
        super(message);
    }
    
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ImportException(String format, String resourceType, String message) {
        super(String.format("Erreur lors de l'importation au format '%s' pour la ressource '%s': %s", 
                format, resourceType, message));
    }
} 