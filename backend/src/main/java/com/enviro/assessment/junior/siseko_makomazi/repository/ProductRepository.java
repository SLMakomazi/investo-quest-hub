package com.enviro.assessment.junior.siseko_makomazi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.junior.siseko_makomazi.model.Product;

/**
 * Repository interface for Product entity.
 * Provides CRUD operations for product data.
 */
// JpaRepository<Product, Long> gives built-in methods like findById, save, and findAll.
// ProductRepository is injected into WithdrawalService to load and update product balances.
public interface ProductRepository extends JpaRepository<Product, Long> {}
