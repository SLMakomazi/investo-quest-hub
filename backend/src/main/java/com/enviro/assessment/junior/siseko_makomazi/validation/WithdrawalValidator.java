package com.enviro.assessment.junior.siseko_makomazi.validation;

import org.springframework.stereotype.Component;

import com.enviro.assessment.junior.siseko_makomazi.exception.AgeRestrictionException;
import com.enviro.assessment.junior.siseko_makomazi.exception.InsufficientBalanceException;
import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Enforces all business rules for a withdrawal.
 *  1. Retirement products: investor age must be > 65
 *  2. Amount must not exceed product balance
 *  3. Amount must not exceed 90% of balance
 */
@Component
public class WithdrawalValidator {

    // MAX_RATIO is declared here as the 90% rule used for every withdrawal check.
    private static final BigDecimal MAX_RATIO = new BigDecimal("0.90");

    public void validate(Investor investor, Product product, BigDecimal amount) {
        // investor, product, and amount are declared as method parameters from the service layer.
        // errors is declared here to reuse validateAll while preserving the older exception-based API.
        List<String> errors = validateAll(investor, product, amount);
        if (errors.isEmpty()) {
            // This if exists because no failed rules means the withdrawal is valid.
            return;
        }

        // message is declared here as one combined error message for API error responses.
        String message = String.join("; ", errors);
        if (errors.get(0).startsWith("Retirement withdrawals")) {
            // This if exists so age failures can still be represented by the age-specific exception type.
            throw new AgeRestrictionException(
                message);
        }

        // Balance and 90% failures use the insufficient-balance exception type.
        throw new InsufficientBalanceException(message);
    }

    public List<String> validateAll(Investor investor, Product product, BigDecimal amount) {
        // errors is declared here and filled with every failed business rule.
        // Returning all errors lets rejected history show all reasons, not only the first one.
        List<String> errors = new ArrayList<>();

        if ("RETIREMENT".equalsIgnoreCase(product.getType()) && investor.getAge() <= 65) {
            // This if checks the retirement age rule: retirement products require age greater than 65.
            errors.add("Retirement withdrawals are only allowed for investors older than 65.");
        }

        if (amount.compareTo(product.getBalance()) > 0) {
            // This if checks that the requested amount is not more than the available balance.
            errors.add("Withdrawal amount exceeds available balance.");
        }

        // max is declared here as 90% of the current product balance, rounded to cents.
        BigDecimal max = product.getBalance().multiply(MAX_RATIO).setScale(2, RoundingMode.HALF_UP);
        if (amount.compareTo(max) > 0) {
            // This if checks the assessment rule that users may withdraw at most 90% of the balance.
            errors.add("Withdrawal cannot exceed 90% of the product balance (max: " + max + ").");
        }

        // The caller decides whether an empty list means approved or a populated list means rejected.
        return errors;
    }
}
