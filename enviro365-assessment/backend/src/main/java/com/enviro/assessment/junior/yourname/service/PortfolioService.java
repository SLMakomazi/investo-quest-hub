package com.enviro.assessment.junior.yourname.service;

import com.enviro.assessment.junior.yourname.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.yourname.model.Portfolio;
import com.enviro.assessment.junior.yourname.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {
    private final PortfolioRepository repo;
    public PortfolioService(PortfolioRepository repo) { this.repo = repo; }

    public Portfolio getByInvestorId(Long investorId) {
        return repo.findByInvestorId(investorId).orElseThrow(
            () -> new ResourceNotFoundException("Portfolio not found for investor: " + investorId));
    }
}
