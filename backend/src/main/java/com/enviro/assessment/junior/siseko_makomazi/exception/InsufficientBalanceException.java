package com.enviro.assessment.junior.siseko_makomazi.exception;

/**
 * Exception thrown when a withdrawal amount exceeds the available balance
 * or exceeds the 90% withdrawal limit.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) { super(message); }
}
