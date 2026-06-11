package com.enviro.assessment.junior.siseko_makomazi.exception;

/**
 * Exception thrown when a withdrawal amount exceeds the available balance
 * or exceeds the 90% withdrawal limit.
 */
public class InsufficientBalanceException extends RuntimeException {
    // message is declared as a constructor parameter and explains the balance or 90% failure.
    public InsufficientBalanceException(String message) { super(message); }
}
