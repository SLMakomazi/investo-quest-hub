package com.enviro.assessment.junior.siseko_makomazi.service;

import org.springframework.stereotype.Service;

import com.enviro.assessment.junior.siseko_makomazi.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.siseko_makomazi.model.Portfolio;
import com.enviro.assessment.junior.siseko_makomazi.repository.PortfolioRepository;

/**
 * Service layer for portfolio-related business logic.
 * Handles portfolio retrieval operations with proper error handling.
 */
@Service
public class PortfolioService {
    // repo is declared here as the PortfolioRepository dependency for database queries.
    // The service uses it to retrieve portfolio rows and related product data.
    private final PortfolioRepository repo;
    
    // repo is declared as a constructor parameter; Spring injects it automatically.
    public PortfolioService(PortfolioRepository repo) { this.repo = repo; }

    /**
     * Retrieve portfolio by investor ID.
     * @param investorId The investor ID
     * @return Portfolio entity with products and investor details
     * @throws ResourceNotFoundException if portfolio not found
     */
    public Portfolio getByInvestorId(Long investorId) {
        // investorId is declared as the method parameter and comes from the API path.
        // If no portfolio exists, the custom exception is handled by GlobalExceptionHandler.
        return repo.findByInvestorId(investorId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found for investor: " + investorId));
    }
}
