package com.enviro.assessment.junior.siseko_makomazi;

import org.junit.jupiter.api.Test;

import com.enviro.assessment.junior.siseko_makomazi.exception.AgeRestrictionException;
import com.enviro.assessment.junior.siseko_makomazi.exception.InsufficientBalanceException;
import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.model.Product;
import com.enviro.assessment.junior.siseko_makomazi.validation.WithdrawalValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawalValidatorTest {

    private final WithdrawalValidator validator = new WithdrawalValidator();

    private Investor investor(int age) {
        Investor i = new Investor(); i.setAge(age); return i;
    }
    private Product product(String type, String balance) {
        Product p = new Product(); p.setType(type); p.setBalance(new BigDecimal(balance)); return p;
    }

    @Test
    void retirementBelow65_throws() {
        assertThrows(AgeRestrictionException.class, () ->
            validator.validate(investor(60), product("RETIREMENT", "100000"), new BigDecimal("1000")));
    }

    @Test
    void amountExceedsBalance_throws() {
        assertThrows(InsufficientBalanceException.class, () ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("2000")));
    }

    @Test
    void amountExceeds90Percent_throws() {
        assertThrows(InsufficientBalanceException.class, () ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("950")));
    }

    @Test
    void validRetirementWithdrawal_passes() {
        assertDoesNotThrow(() ->
            validator.validate(investor(70), product("RETIREMENT", "1000"), new BigDecimal("900")));
    }

    @Test
    void savingsAnyAge_passes() {
        assertDoesNotThrow(() ->
            validator.validate(investor(25), product("SAVINGS", "1000"), new BigDecimal("500")));
    }
}
