package com.enviro.assessment.junior.yourname.repository;

import com.enviro.assessment.junior.yourname.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, Long> {}
