package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Investor;

/**
 * Repository interface for Investor entity.
 * Provides CRUD operations for investor data.
 */
// JpaRepository<Investor, Long> means this repository manages Investor entities with Long IDs.
// Spring Data creates the implementation automatically at runtime.
public interface InvestorRepository extends JpaRepository<Investor, Long> {}
