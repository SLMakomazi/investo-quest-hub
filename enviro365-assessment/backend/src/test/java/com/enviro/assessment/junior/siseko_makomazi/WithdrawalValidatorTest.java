package com.enviro.assessment.junior.siseko_makomazi;

import org.junit.jupiter.api.Test;

import com.enviro.assessment.junior.siseko_makomazi.exception.AgeRestrictionException;
import com.enviro.assessment.junior.siseko_makomazi.exception.InsufficientBalanceException;
import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.model.Product;
import com.enviro.assessment.junior.siseko_makomazi.validation.WithdrawalValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WithdrawalValidator.
 * Tests all business rules for withdrawal validation:
 * - Retirement withdrawals require age > 65
 * - Withdrawal amount cannot exceed product balance
 * - Withdrawal amount cannot exceed 90% of balance
 * - Savings products have no age restriction
 */
class WithdrawalValidatorTest {

    private final WithdrawalValidator validator = new WithdrawalValidator();

    /**
     * Helper method to create an Investor with a specific age.
     * @param age The investor's age
     * @return Investor entity with specified age
     */
    private Investor investor(int age) {
        Investor i = new Investor(); i.setAge(age); return i;
    }

    /**
     * Helper method to create a Product with specific type and balance.
     * @param type The product type (RETIREMENT or SAVINGS)
     * @param balance The product balance as a string
     * @return Product entity with specified type and balance
     */
    private Product product(String type, String balance) {
        Product p = new Product(); p.setType(type); p.setBalance(new BigDecimal(balance)); return p;
    }

    /**
     * Test that retirement withdrawals fail for investors under 65.
     * Should throw AgeRestrictionException.
     */
    @Test
    void retirementBelow65_throws() {
        assertThrows(AgeRestrictionException.class, () ->
            validator.validate(investor(60), product("RETIREMENT", "100000"), new BigDecimal("1000")));
    }

    /**
     * Test that withdrawals exceeding the product balance fail.
     * Should throw InsufficientBalanceException.
     */
    @Test
    void amountExceedsBalance_throws() {
        assertThrows(InsufficientBalanceException.class, () ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("2000")));
    }

    /**
     * Test that withdrawals exceeding 90% of the balance fail.
     * Should throw InsufficientBalanceException.
     */
    @Test
    void amountExceeds90Percent_throws() {
        assertThrows(InsufficientBalanceException.class, () ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("950")));
    }

    /**
     * Test that valid retirement withdrawals pass all validations.
     * Investor is over 65, amount is within balance and 90% limit.
     */
    @Test
    void validRetirementWithdrawal_passes() {
        assertDoesNotThrow(() ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("900")));
    }

    /**
     * Test that savings product withdrawals have no age restriction.
     * Should pass even if investor is under 65.
     */
    @Test
    void savingsAnyAge_passes() {
        assertDoesNotThrow(() ->
            validator.validate(investor(25), product("SAVINGS", "1000"), new BigDecimal("500")));
    }
}
