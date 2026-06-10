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

        // Validate withdrawal against business rules (age, balance, 90% limit)
        validator.validate(investor, product, req.amount);

        // Update product balance by subtracting withdrawal amount
        product.setBalance(product.getBalance().subtract(req.amount));
        productRepo.save(product);

        // Create and save withdrawal record
        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(req.amount);
        Withdrawal saved = withdrawalRepo.save(w);

        // Build response DTO with withdrawal details
        WithdrawalResponseDTO out = new WithdrawalResponseDTO();
        out.id = saved.getId();
        out.investorId = investor.getId();
        out.productId = product.getId();
        out.productName = product.getName();
        out.amount = saved.getAmount();
        out.remainingBalance = product.getBalance();
        out.createdAt = saved.getCreatedAt();
        return out;
    }

    /**
     * Retrieve withdrawal history for an investor.
     * Returns withdrawals ordered by creation date (newest first).
     * @param investorId The investor ID
     * @return List of withdrawal entities
     */
    public List<Withdrawal> history(Long investorId) {
        return withdrawalRepo.findByInvestorIdOrderByCreatedAtDesc(investorId);
    }
}
