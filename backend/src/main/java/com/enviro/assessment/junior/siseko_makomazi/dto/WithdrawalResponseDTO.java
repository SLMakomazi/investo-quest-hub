package com.enviro.assessment.junior.siseko_makomazi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for withdrawal response.
 * Contains the result of a successful withdrawal including the remaining balance.
 * Provides feedback to the frontend about the withdrawal outcome.
 */
public class WithdrawalResponseDTO {
    public Long id;
    public Long investorId;
    public Long productId;
    public String productName;
    public BigDecimal amount;
    public BigDecimal remainingBalance;  // Balance after withdrawal
    public LocalDateTime createdAt;
}
