package com.project.taskmanager.exception;

import com.project.taskmanager.dto.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        logger.warning("Validation error occurred: " + errors.toString());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Status> handleIllegalArgumentException(IllegalArgumentException ex) {
        Status status = new Status(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        logger.log(Level.WARNING, "IllegalArgumentException occurred: " + ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Status> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Status status = new Status(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
        logger.log(Level.WARNING, "ResourceNotFoundException occurred: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Status> handleConstraintViolation(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        Status status = new Status(HttpStatus.BAD_REQUEST.toString(), errorMessage);
        logger.log(Level.WARNING, "ConstraintViolationException occurred: " + errorMessage, ex);
        return ResponseEntity.badRequest().body(status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Status> handleAllUncaughtException(Exception ex) {
        logger.log(Level.SEVERE, "Unhandled exception occurred: " + ex.getMessage(), ex);
        Status status = new Status(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        String rootCauseMessage = (ex.getCause() != null) ? ex.getCause().toString() : "No root cause";
        status.setMessage(status.getMessage() + "\nRoot Cause: " + rootCauseMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
    }
}
