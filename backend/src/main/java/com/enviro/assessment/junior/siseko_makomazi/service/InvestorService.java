package com.enviro.assessment.junior.siseko_makomazi.service;

import org.springframework.stereotype.Service;

import com.enviro.assessment.junior.siseko_makomazi.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.repository.InvestorRepository;

/**
 * Service layer for investor-related business logic.
 * Handles investor retrieval operations with proper error handling.
 */
@Service
public class InvestorService {
    private final InvestorRepository repo;
    
    // Constructor injection of InvestorRepository
    public InvestorService(InvestorRepository repo) { this.repo = repo; }

    /**
     * Retrieve investor by ID.
     * @param id The investor ID
     * @return Investor entity
     * @throws ResourceNotFoundException if investor not found
     */
    public Investor getById(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + id));
    }
}
