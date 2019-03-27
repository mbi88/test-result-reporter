package com.mbi.api.controllers;

import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
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
                        ex.getMessage(), ex.getError(),
                        new Timestamp(System.currentTimeMillis()), ex.getException()
                ),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(final AlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.CONFLICT.value(),
                        ex.getMessage(), ex.getError(),
                        new Timestamp(System.currentTimeMillis()), ex.getException()
                ),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(), ex.getError(),
                        new Timestamp(System.currentTimeMillis()), ex.getException()
                ),
                HttpStatus.BAD_REQUEST);
    }

    private class ErrorResponse {
        private final int status;
        private final String message;
        private final String error;
        private final Timestamp timestamp;
        private final String exception;

        private ErrorResponse(int status, String message, String error, Timestamp timestamp, String exception) {
            this.status = status;
            this.message = message;
            this.error = error;
            this.timestamp = timestamp;
            this.exception = exception;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getError() {
            return error;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public String getException() {
            return exception;
        }
    }
}
