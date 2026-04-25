package com.devapix.auth_service.advice;

import com.devapix.auth_service.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                400);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}