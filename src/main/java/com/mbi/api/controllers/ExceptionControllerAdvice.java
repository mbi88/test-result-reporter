package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                        ex.getError(),
                        ex.getException(),
                        ex.getMessage(),
                        new Timestamp(System.currentTimeMillis())),
                HttpStatus.NOT_FOUND);
    }

    private class ErrorResponse {
        private final int status;
        private final String error;
        private final String exception;
        private final String message;
        private final Timestamp timestamp;

        private ErrorResponse(int status, String error, String exception, String message, Timestamp timestamp) {
            this.status = status;
            this.error = error;
            this.exception = exception;
            this.message = message;
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getException() {
            return exception;
        }

        public String getMessage() {
            return message;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }
    }
}
