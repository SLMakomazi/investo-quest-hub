package com.enviro.assessment.junior.yourname.service;

import com.enviro.assessment.junior.yourname.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.yourname.model.Investor;
import com.enviro.assessment.junior.yourname.repository.InvestorRepository;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {
    private final InvestorRepository repo;
    public InvestorService(InvestorRepository repo) { this.repo = repo; }

    public Investor getById(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Investor not found: " + id));
    }
}
