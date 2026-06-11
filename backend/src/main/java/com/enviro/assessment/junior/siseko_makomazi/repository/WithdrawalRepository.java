package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Withdrawal;

import java.util.List;

/**
 * Repository interface for Withdrawal entity.
 * Provides CRUD operations and custom queries for withdrawal data.
 */
// JpaRepository<Withdrawal, Long> gives built-in save/find methods for withdrawal history rows.
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    /**
     * Find all withdrawals for an investor, ordered by creation date (newest first).
     * @param investorId The investor ID
     * @return List of withdrawals ordered by creation date descending
     */
    // investorId is declared as the method parameter and filters records by Withdrawal.investor.id.
    // OrderByCreatedAtDesc makes the newest approved/rejected submissions appear first.
    List<Withdrawal> findByInvestorIdOrderByCreatedAtDesc(Long investorId);
}
