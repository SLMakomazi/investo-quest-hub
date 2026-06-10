package com.enviro.assessment.junior.siseko_makomazi.validation;

import org.springframework.stereotype.Component;

import com.enviro.assessment.junior.siseko_makomazi.exception.AgeRestrictionException;
import com.enviro.assessment.junior.siseko_makomazi.exception.InsufficientBalanceException;
import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Enforces all business rules for a withdrawal.
 *  1. Retirement products: investor age must be > 65
 *  2. Amount must not exceed product balance
 *  3. Amount must not exceed 90% of balance
 */
@Component
public class WithdrawalValidator {

    private static final BigDecimal MAX_RATIO = new BigDecimal("0.90");

    public void validate(Investor investor, Product product, BigDecimal amount) {
        if ("RETIREMENT".equalsIgnoreCase(product.getType()) && investor.getAge() <= 65) {
            throw new AgeRestrictionException(
                "Retirement withdrawals are only allowed for investors older than 65.");
        }
        if (amount.compareTo(product.getBalance()) > 0) {
            throw new InsufficientBalanceException(
                "Withdrawal amount exceeds available balance.");
        }
        BigDecimal max = product.getBalance().multiply(MAX_RATIO).setScale(2, RoundingMode.HALF_UP);
        if (amount.compareTo(max) > 0) {
            throw new InsufficientBalanceException(
                "Withdrawal cannot exceed 90% of the product balance (max: " + max + ").");
        }
    }
}
