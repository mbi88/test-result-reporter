package com.mbi.api.controllers;

import com.mbi.api.exceptions.AbstractException;
import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

/**
 * Exception controller advice.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.NOT_FOUND, ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(final AlreadyExistsException ex) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.CONFLICT, ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException ex) {
        return new ResponseEntity<>(getErrorResponse(HttpStatus.BAD_REQUEST, ex), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(final HttpStatus httpStatus, final AbstractException ex) {
        return new ErrorResponse(httpStatus.value(), ex.getMessage(), ex.getError(),
                new Timestamp(System.currentTimeMillis()), ex.getException());
    }

    /**
     * Error response model.
     */
    private static final class ErrorResponse {
        private final int status;
        private final String message;
        private final String error;
        private final Timestamp timestamp;
        private final String exception;

        private ErrorResponse(final int status, final String message, final String error, final Timestamp timestamp,
                              final String exception) {
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
