package com.enviro.assessment.junior.yourname.service;

import com.enviro.assessment.junior.yourname.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.yourname.dto.WithdrawalResponseDTO;
import com.enviro.assessment.junior.yourname.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.yourname.model.*;
import com.enviro.assessment.junior.yourname.repository.*;
import com.enviro.assessment.junior.yourname.validation.WithdrawalValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WithdrawalService {

    private final InvestorRepository investorRepo;
    private final ProductRepository productRepo;
    private final WithdrawalRepository withdrawalRepo;
    private final WithdrawalValidator validator;

    public WithdrawalService(InvestorRepository i, ProductRepository p,
                             WithdrawalRepository w, WithdrawalValidator v) {
        this.investorRepo = i; this.productRepo = p; this.withdrawalRepo = w; this.validator = v;
    }

    @Transactional
    public WithdrawalResponseDTO create(WithdrawalRequestDTO req) {
        Investor investor = investorRepo.findById(req.investorId).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + req.investorId));
        Product product = productRepo.findById(req.productId).orElseThrow(
            () -> new ResourceNotFoundException("Product not found: " + req.productId));

        validator.validate(investor, product, req.amount);

        product.setBalance(product.getBalance().subtract(req.amount));
        productRepo.save(product);

        Withdrawal w = new Withdrawal();
        w.setInvestor(investor);
        w.setProduct(product);
        w.setAmount(req.amount);
        Withdrawal saved = withdrawalRepo.save(w);

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

    public List<Withdrawal> history(Long investorId) {
        return withdrawalRepo.findByInvestorIdOrderByCreatedAtDesc(investorId);
    }
}
