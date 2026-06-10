package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Withdrawal;

import java.util.List;

/**
 * Repository interface for Withdrawal entity.
 * Provides CRUD operations and custom queries for withdrawal data.
 */
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    /**
     * Find all withdrawals for an investor, ordered by creation date (newest first).
     * @param investorId The investor ID
     * @return List of withdrawals ordered by creation date descending
     */
    List<Withdrawal> findByInvestorIdOrderByCreatedAtDesc(Long investorId);
}
