package com.enviro.assessment.junior.siseko_makomazi.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object for Portfolio information.
 * Contains portfolio details, investor information, and associated products.
 * Used to send complete portfolio data to the frontend in a single API call.
 */
public class PortfolioDTO {
    // id is declared here as the portfolio's unique database identifier.
    public Long id;
    // name is declared here as the portfolio display name.
    public String name;
    // investor is declared here to include owner details in one response.
    public InvestorDTO investor;
    // products is declared here as the list of investment products in this portfolio.
    public List<ProductDTO> products;

    /**
     * Nested DTO for Product information within a portfolio.
     * Simplifies product data structure for API responses.
     */
    public static class ProductDTO {
        // id is declared here as the product's unique database identifier.
        public Long id;
        // type is declared here because RETIREMENT products have extra withdrawal rules.
        public String type;  // RETIREMENT or SAVINGS
        // name is declared here as the product display name shown in the UI.
        public String name;
        // balance is declared here as the product's current available amount.
        public BigDecimal balance;
    }
}
