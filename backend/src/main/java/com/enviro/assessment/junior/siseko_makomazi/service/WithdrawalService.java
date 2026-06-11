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

    // investorRepo is declared here to load the investor linked to a withdrawal request.
    private final InvestorRepository investorRepo;
    // productRepo is declared here to load and update the selected product balance.
    private final ProductRepository productRepo;
    // withdrawalRepo is declared here to save approved/rejected withdrawals and fetch history.
    private final WithdrawalRepository withdrawalRepo;
    // validator is declared here to keep business-rule checks in one reusable class.
    private final WithdrawalValidator validator;

    // i, p, w, and v are declared as constructor parameters.
    // Spring injects the matching repository/service beans and the constructor assigns them to fields.
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
        // req is declared as the method parameter and contains investorId, productId, and amount.
        // investor is declared here after loading the investor record from the database.
        Investor investor = investorRepo.findById(req.investorId).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + req.investorId));
        // product is declared here after loading the product selected in the withdrawal form.
        Product product = productRepo.findById(req.productId).orElseThrow(
            () -> new ResourceNotFoundException("Product not found: " + req.productId));

        // validationErrors is declared here as the list of failed business rules.
        // It can contain one reason or multiple reasons, for example age + balance + 90% limit.
        List<String> validationErrors = validator.validateAll(investor, product, req.amount);
        if (!validationErrors.isEmpty()) {
            // The if exists because rejected withdrawals must be saved without changing the balance.
            // rejectionReason is declared here by joining all failed rule messages into one readable string.
            String rejectionReason = String.join("; ", validationErrors);
            // saved is declared here as the rejected withdrawal row returned by the repository.
            Withdrawal saved = saveRejectedWithdrawal(investor, product, req.amount, rejectionReason);
            // The current product balance is returned because rejected withdrawals do not subtract money.
            return toResponse(saved, product.getBalance());
        }

        // This block only runs when there are no validation errors, so the withdrawal is approved.
        // The product balance is reduced by the requested amount.
        product.setBalance(product.getBalance().subtract(req.amount));
        productRepo.save(product);

        // w is declared here as a new Withdrawal entity that will be saved as APPROVED.
        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(req.amount);
        w.setStatus("APPROVED");
        // saved is declared here as the database version of the approved withdrawal, including its ID.
        Withdrawal saved = withdrawalRepo.save(w);

        // The response includes the updated balance after the approved withdrawal.
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
        // investor, product, amount, and reason are declared as method parameters.
        // w is declared here as the entity that records the rejected attempt in history.
        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(amount);
        w.setStatus("REJECTED");
        w.setRejectionReason(reason);
        // Saving this row gives the UI an audit trail even though no balance changed.
        return withdrawalRepo.save(w);
    }

    private WithdrawalResponseDTO toResponse(Withdrawal withdrawal, java.math.BigDecimal remainingBalance) {
        // withdrawal and remainingBalance are declared as method parameters.
        // out is declared here as the DTO sent back to the frontend.
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
        // Returning a DTO avoids exposing more entity data than the UI needs after submit.
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
        // investorId is declared as the method parameter and filters history to one investor.
        // The repository method name tells Spring Data to order the records newest first.
        return withdrawalRepo.findByInvestorIdOrderByCreatedAtDesc(investorId);
    }
}
