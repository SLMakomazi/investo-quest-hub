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
        // status and message are declared as method parameters by each exception handler.
        // m is declared here as the JSON response body sent back to the frontend.
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
        // ex is declared by Spring when an AgeRestrictionException is thrown.
        // 403 is used because the investor is not allowed to perform this retirement withdrawal.
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    /**
     * Handle insufficient balance exceptions.
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> handleBalance(InsufficientBalanceException ex) {
        // ex contains the balance or 90% rule error message.
        // 400 is used because the submitted request amount is invalid for the product.
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Handle resource not found exceptions.
     * Returns HTTP 404 Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        // ex contains which missing resource was requested, such as investor or product.
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Handle validation exceptions from Jakarta Bean Validation annotations.
     * Returns HTTP 400 Bad Request with field-specific error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        // ex contains validation errors created by annotations like @NotNull and @Positive.
        // msg is declared here by combining all field errors into one readable response.
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
        // This fallback if no specific handler matched prevents raw stack traces from reaching the UI.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
