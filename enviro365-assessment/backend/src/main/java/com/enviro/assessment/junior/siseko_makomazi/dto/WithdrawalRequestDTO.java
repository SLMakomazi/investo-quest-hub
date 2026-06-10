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
    @NotNull  // Investor ID is required
    public Long investorId;
    
    @NotNull  // Product ID is required
    public Long productId;
    
    @NotNull @Positive  // Amount is required and must be positive
    public BigDecimal amount;
}
