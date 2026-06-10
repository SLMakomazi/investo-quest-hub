package com.enviro.assessment.junior.siseko_makomazi.controller;

import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.junior.siseko_makomazi.model.Portfolio;
import com.enviro.assessment.junior.siseko_makomazi.service.PortfolioService;

/**
 * REST controller for portfolio-related operations.
 * Provides endpoints to retrieve portfolio information including products.
 */
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    private final PortfolioService service;
    
    // Constructor injection of PortfolioService
    public PortfolioController(PortfolioService s) { this.service = s; }

    /**
     * Get portfolio by investor ID.
     * Returns the portfolio with all associated products and investor details.
     * @param investorId The investor ID
     * @return Portfolio entity with products and investor
     */
    @GetMapping("/investor/{investorId}")
    public Portfolio byInvestor(@PathVariable Long investorId) {
        return service.getByInvestorId(investorId);
    }
}
