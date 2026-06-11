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
    // repo is declared here as the InvestorRepository dependency for database access.
    // It is final because the service should use the same repository instance after construction.
    private final InvestorRepository repo;
    
    // repo is declared as a constructor parameter; Spring injects the repository bean.
    public InvestorService(InvestorRepository repo) { this.repo = repo; }

    /**
     * Retrieve investor by ID.
     * @param id The investor ID
     * @return Investor entity
     * @throws ResourceNotFoundException if investor not found
     */
    public Investor getById(Long id) {
        // id is declared as the method parameter and contains the investor primary key.
        // orElseThrow is used so missing investors become a controlled 404 error.
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + id));
    }
}
