package com.enviro.assessment.junior.siseko_makomazi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Catches all exceptions and returns structured JSON error responses.
 * Provides consistent error handling across all REST endpoints.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Build a standardized error response body.
     * @param status HTTP status code
     * @param message Error message
     * @return Map containing timestamp, status, error type, and message
     */
    private Map<String, Object> body(HttpStatus status, String message) {
        Map<String, Object> m = new HashMap<>();
        m.put("timestamp", LocalDateTime.now());
        m.put("status", status.value());
        m.put("error", status.getReasonPhrase());
        m.put("message", message);
        return m;
    }

    /**
     * Handle age restriction exceptions (investor too young for retirement withdrawal).
     * Returns HTTP 403 Forbidden.
     */
    @ExceptionHandler(AgeRestrictionException.class)
    public ResponseEntity<?> handleAge(AgeRestrictionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    /**
     * Handle insufficient balance exceptions.
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> handleBalance(InsufficientBalanceException ex) {
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Handle resource not found exceptions.
     * Returns HTTP 404 Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Handle validation exceptions from Jakarta Bean Validation annotations.
     * Returns HTTP 400 Bad Request with field-specific error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b).orElse("Validation failed");
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, msg));
    }

    /**
     * Handle all other exceptions as a fallback.
     * Returns HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
