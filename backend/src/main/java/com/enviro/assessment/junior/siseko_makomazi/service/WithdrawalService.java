package com.enviro.assessment.junior.siseko_makomazi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.junior.siseko_makomazi.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.siseko_makomazi.dto.WithdrawalResponseDTO;
import com.enviro.assessment.junior.siseko_makomazi.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.siseko_makomazi.model.*;
import com.enviro.assessment.junior.siseko_makomazi.repository.*;
import com.enviro.assessment.junior.siseko_makomazi.validation.WithdrawalValidator;

import java.util.List;

/**
 * Service layer for withdrawal-related business logic.
 * Handles withdrawal creation, validation, balance updates, and history retrieval.
 * Saves rejected withdrawals for audit trail purposes.
 */
@Service
public class WithdrawalService {

    private final InvestorRepository investorRepo;
    private final ProductRepository productRepo;
    private final WithdrawalRepository withdrawalRepo;
    private final WithdrawalValidator validator;

    // Constructor injection of all required dependencies
    public WithdrawalService(InvestorRepository i, ProductRepository p,
                             WithdrawalRepository w, WithdrawalValidator v) {
        this.investorRepo = i; this.productRepo = p; this.withdrawalRepo = w; this.validator = v;
    }

    /**
     * Create a new withdrawal after validating business rules.
     * Updates product balance and records the withdrawal transaction.
     * If validation fails, saves the withdrawal as REJECTED for audit trail.
     * @param req The withdrawal request with investor ID, product ID, and amount
     * @return WithdrawalResponseDTO with withdrawal details and remaining balance
     * @throws ResourceNotFoundException if investor or product not found
     */
    @Transactional
    public WithdrawalResponseDTO create(WithdrawalRequestDTO req) {
        // Retrieve investor and product, throw if not found
        Investor investor = investorRepo.findById(req.investorId).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + req.investorId));
        Product product = productRepo.findById(req.productId).orElseThrow(
            () -> new ResourceNotFoundException("Product not found: " + req.productId));

        List<String> validationErrors = validator.validateAll(investor, product, req.amount);
        if (!validationErrors.isEmpty()) {
            String rejectionReason = String.join("; ", validationErrors);
            Withdrawal saved = saveRejectedWithdrawal(investor, product, req.amount, rejectionReason);
            return toResponse(saved, product.getBalance());
        }

        // Update product balance by subtracting withdrawal amount
        product.setBalance(product.getBalance().subtract(req.amount));
        productRepo.save(product);

        // Create and save withdrawal record as APPROVED
        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(req.amount);
        w.setStatus("APPROVED");
        Withdrawal saved = withdrawalRepo.save(w);

        return toResponse(saved, product.getBalance());
    }

    /**
     * Helper method to save a rejected withdrawal for audit trail.
     * Does not update product balance since withdrawal was rejected.
     * @param investor The investor who attempted the withdrawal
     * @param product The product from which withdrawal was attempted
     * @param amount The withdrawal amount requested
     * @param reason The reason for rejection
     */
    private Withdrawal saveRejectedWithdrawal(Investor investor, Product product, 
                                        java.math.BigDecimal amount, String reason) {
        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(amount);
        w.setStatus("REJECTED");
        w.setRejectionReason(reason);
        return withdrawalRepo.save(w);
    }

    private WithdrawalResponseDTO toResponse(Withdrawal withdrawal, java.math.BigDecimal remainingBalance) {
        WithdrawalResponseDTO out = new WithdrawalResponseDTO();
        out.id = withdrawal.getId();
        out.investorId = withdrawal.getInvestor().getId();
        out.productId = withdrawal.getProduct().getId();
        out.productName = withdrawal.getProduct().getName();
        out.amount = withdrawal.getAmount();
        out.remainingBalance = remainingBalance;
        out.createdAt = withdrawal.getCreatedAt();
        out.status = withdrawal.getStatus();
        out.rejectionReason = withdrawal.getRejectionReason();
        return out;
    }

    /**
     * Retrieve withdrawal history for an investor.
     * Returns withdrawals ordered by creation date (newest first).
     * Includes both approved and rejected withdrawals.
     * @param investorId The investor ID
     * @return List of withdrawal entities
     */
    public List<Withdrawal> history(Long investorId) {
        return withdrawalRepo.findByInvestorIdOrderByCreatedAtDesc(investorId);
    }
}
