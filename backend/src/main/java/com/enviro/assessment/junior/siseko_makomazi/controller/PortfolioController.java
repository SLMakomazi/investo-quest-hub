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
    // service is declared here and assigned through constructor injection.
    // The controller uses it to fetch portfolio data instead of calling the repository directly.
    private final PortfolioService service;
    
    // s is declared as a constructor parameter; Spring passes in PortfolioService automatically.
    public PortfolioController(PortfolioService s) { this.service = s; }

    /**
     * Get portfolio by investor ID.
     * Returns the portfolio with all associated products and investor details.
     * @param investorId The investor ID
     * @return Portfolio entity with products and investor
     */
    @GetMapping("/investor/{investorId}")
    public Portfolio byInvestor(@PathVariable Long investorId) {
        // investorId is declared from the URL path.
        // It identifies which investor's portfolio should be returned to the UI.
        return service.getByInvestorId(investorId);
    }
}
