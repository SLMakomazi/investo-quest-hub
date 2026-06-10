package com.enviro.assessment.junior.siseko_makomazi.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object for Portfolio information.
 * Contains portfolio details, investor information, and associated products.
 * Used to send complete portfolio data to the frontend in a single API call.
 */
public class PortfolioDTO {
    public Long id;
    public String name;
    public InvestorDTO investor;
    public List<ProductDTO> products;

    /**
     * Nested DTO for Product information within a portfolio.
     * Simplifies product data structure for API responses.
     */
    public static class ProductDTO {
        public Long id;
        public String type;  // RETIREMENT or SAVINGS
        public String name;
        public BigDecimal balance;
    }
}
