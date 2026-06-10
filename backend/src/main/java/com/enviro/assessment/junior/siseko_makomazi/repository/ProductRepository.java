package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Product;

/**
 * Repository interface for Product entity.
 * Provides CRUD operations for product data.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {}
