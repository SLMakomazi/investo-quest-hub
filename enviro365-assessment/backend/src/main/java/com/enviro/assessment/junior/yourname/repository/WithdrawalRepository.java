package com.enviro.assessment.junior.yourname.repository;

import com.enviro.assessment.junior.yourname.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByInvestorIdOrderByCreatedAtDesc(Long investorId);
}
