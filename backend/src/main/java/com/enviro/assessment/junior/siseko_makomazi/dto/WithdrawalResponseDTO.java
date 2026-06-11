package com.enviro.assessment.junior.siseko_makomazi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for withdrawal response.
 * Contains the result of a successful withdrawal including the remaining balance.
 * Provides feedback to the frontend about the withdrawal outcome.
 */
public class WithdrawalResponseDTO {
    // id is declared here as the saved withdrawal record ID.
    public Long id;
    // investorId is declared here so the frontend knows who the result belongs to.
    public Long investorId;
    // productId is declared here so the frontend can connect the result to a product.
    public Long productId;
    // productName is declared here for friendly UI messages.
    public String productName;
    // amount is declared here as the withdrawal amount that was submitted.
    public BigDecimal amount;
    // remainingBalance is declared here as the product balance after approval, or unchanged after rejection.
    public BigDecimal remainingBalance;  // Balance after withdrawal
    // createdAt is declared here as the timestamp of the withdrawal history row.
    public LocalDateTime createdAt;
    // status is declared here to tell the UI whether the request was APPROVED or REJECTED.
    public String status;
    // rejectionReason is declared here to explain failed business rules for rejected withdrawals.
    public String rejectionReason;
}
