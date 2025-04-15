package com.parfinfo.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String details;

    public ApiException(String message, HttpStatus status) {
        this(message, status, null, null);
    }

    public ApiException(String message, HttpStatus status, String code) {
        this(message, status, code, null);
    }

    public ApiException(String message, HttpStatus status, String code, String details) {
        super(message);
        this.status = status;
        this.code = code;
        this.details = details;
    }

    public static class Builder {
        private String message;
        private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        private String code;
        private String details;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public ApiException build() {
            return new ApiException(message, status, code, details);
        }
    }
} 