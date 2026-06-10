package com.enviro.assessment.junior.yourname.repository;

import com.enviro.assessment.junior.yourname.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
