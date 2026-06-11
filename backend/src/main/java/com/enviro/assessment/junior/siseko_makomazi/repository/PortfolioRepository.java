package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Portfolio;

import java.util.Optional;

/**
 * Repository interface for Portfolio entity.
 * Provides CRUD operations and custom queries for portfolio data.
 */
// JpaRepository<Portfolio, Long> gives built-in CRUD methods for Portfolio records.
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    /**
     * Find portfolio by investor ID.
     * @param investorId The investor ID
     * @return Optional containing the portfolio if found
     */
    // investorId is declared as the method parameter and maps to Portfolio.investor.id.
    // Spring Data reads the method name and builds the SQL query automatically.
    Optional<Portfolio> findByInvestorId(Long investorId);
}
