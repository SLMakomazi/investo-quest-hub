package com.enviro.assessment.junior.siseko_makomazi.exception;

/**
 * Exception thrown when a requested resource (investor, portfolio, or product)
 * cannot be found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}
