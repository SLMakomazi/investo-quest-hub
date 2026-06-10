package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Portfolio;

import java.util.Optional;

/**
 * Repository interface for Portfolio entity.
 * Provides CRUD operations and custom queries for portfolio data.
 */
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    /**
     * Find portfolio by investor ID.
     * @param investorId The investor ID
     * @return Optional containing the portfolio if found
     */
    Optional<Portfolio> findByInvestorId(Long investorId);
}
