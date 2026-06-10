package com.enviro.assessment.junior.siseko_makomazi;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.enviro.assessment.junior.siseko_makomazi.exception.AgeRestrictionException;
import com.enviro.assessment.junior.siseko_makomazi.exception.GlobalExceptionHandler;
import com.enviro.assessment.junior.siseko_makomazi.exception.InsufficientBalanceException;
import com.enviro.assessment.junior.siseko_makomazi.exception.ResourceNotFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GlobalExceptionHandler.
 * Tests how different exceptions are handled and converted to HTTP responses.
 * Demonstrates the error handling mechanisms in the application.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    /**
     * Test that AgeRestrictionException returns HTTP 403 Forbidden.
     * Verifies that the error message is included in the response body.
     */
    @Test
    void handleAgeRestrictionException_returns403() {
        AgeRestrictionException ex = new AgeRestrictionException(
            "Retirement withdrawals are only allowed for investors older than 65.");
        
        ResponseEntity<?> response = handler.handleAge(ex);
        
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(403, body.get("status"));
        assertEquals("Forbidden", body.get("error"));
        assertTrue(body.get("message").toString().contains("older than 65"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test that InsufficientBalanceException returns HTTP 400 Bad Request.
     * Verifies that the error message is included in the response body.
     */
    @Test
    void handleInsufficientBalanceException_returns400() {
        InsufficientBalanceException ex = new InsufficientBalanceException(
            "Withdrawal amount exceeds available balance.");
        
        ResponseEntity<?> response = handler.handleBalance(ex);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertTrue(body.get("message").toString().contains("exceeds available balance"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test that ResourceNotFoundException returns HTTP 404 Not Found.
     * Verifies that the error message is included in the response body.
     */
    @Test
    void handleResourceNotFoundException_returns404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Investor not found: 999");
        
        ResponseEntity<?> response = handler.handleNotFound(ex);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertTrue(body.get("message").toString().contains("Investor not found"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test that validation errors return HTTP 400 Bad Request.
     * Verifies that field-specific error messages are included in the response.
     */
    @Test
    void handleValidationException_returns400WithFieldErrors() {
        // Create a mock validation exception with field errors
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
            null, null);
        
        ResponseEntity<?> response = handler.handleValidation(ex);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertNotNull(body.get("message"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test that generic exceptions return HTTP 500 Internal Server Error.
     * Verifies that the error message is included in the response body.
     */
    @Test
    void handleGenericException_returns500() {
        Exception ex = new Exception("Unexpected error occurred");
        
        ResponseEntity<?> response = handler.handleGeneric(ex);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertTrue(body.get("message").toString().contains("Unexpected error"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test that all error responses have a consistent structure.
     * Verifies that timestamp, status, error type, and message are always present.
     */
    @Test
    void errorResponses_haveConsistentStructure() {
        // Test with ResourceNotFoundException
        ResourceNotFoundException ex = new ResourceNotFoundException("Test error");
        ResponseEntity<?> response = handler.handleNotFound(ex);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        
        // Verify all required fields are present
        assertTrue(body.containsKey("timestamp"));
        assertTrue(body.containsKey("status"));
        assertTrue(body.containsKey("error"));
        assertTrue(body.containsKey("message"));
        
        // Verify data types
        assertNotNull(body.get("timestamp"));
        assertTrue(body.get("status") instanceof Integer);
        assertTrue(body.get("error") instanceof String);
        assertTrue(body.get("message") instanceof String);
    }
}
