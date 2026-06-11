package com.enviro.assessment.junior.siseko_makomazi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Data Transfer Object for withdrawal requests.
 * Contains the information needed to process a withdrawal.
 * Uses Jakarta Bean Validation annotations for input validation.
 */
public class WithdrawalRequestDTO {
    // investorId is declared here so the backend knows which investor is submitting.
    @NotNull  // Investor ID is required
    public Long investorId;
    
    // productId is declared here so the backend knows which product balance to validate/update.
    @NotNull  // Product ID is required
    public Long productId;
    
    // amount is declared here as the requested withdrawal value in ZAR.
    @NotNull @Positive  // Amount is required and must be positive
    public BigDecimal amount;
}
