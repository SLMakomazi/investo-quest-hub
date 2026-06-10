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
    private final PortfolioRepository repo;
    
    // Constructor injection of PortfolioRepository
    public PortfolioService(PortfolioRepository repo) { this.repo = repo; }

    /**
     * Retrieve portfolio by investor ID.
     * @param investorId The investor ID
     * @return Portfolio entity with products and investor details
     * @throws ResourceNotFoundException if portfolio not found
     */
    public Portfolio getByInvestorId(Long investorId) {
        return repo.findByInvestorId(investorId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found for investor: " + investorId));
    }
}
