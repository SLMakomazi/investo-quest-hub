package com.enviro.assessment.junior.siseko_makomazi.exception;

/**
 * Exception thrown when an investor attempts to withdraw from a retirement product
 * but does not meet the age requirement (must be older than 65).
 */
public class AgeRestrictionException extends RuntimeException {
    public AgeRestrictionException(String message) { super(message); }
}
