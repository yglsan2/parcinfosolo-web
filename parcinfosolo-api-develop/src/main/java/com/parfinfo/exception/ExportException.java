package com.parfinfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExportException extends RuntimeException {
    
    public ExportException(String message) {
        super(message);
    }
    
    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ExportException(String format, String resourceType, String message) {
        super(String.format("Erreur lors de l'exportation au format '%s' pour la ressource '%s': %s", 
                format, resourceType, message));
    }
} 