package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Investor;

/**
 * Repository interface for Investor entity.
 * Provides CRUD operations for investor data.
 */
public interface InvestorRepository extends JpaRepository<Investor, Long> {}
