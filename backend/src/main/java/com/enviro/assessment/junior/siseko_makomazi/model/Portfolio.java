package com.enviro.assessment.junior.siseko_makomazi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

/**
 * Represents an investment portfolio belonging to an investor.
 * Contains a collection of products (investment funds) that the investor holds.
 * Eager fetching used to load products with portfolio for API responses.
 */
@Entity
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Investor - eager fetch to always load investor data
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "investor_id")
    private Investor investor;

    // Portfolio name (e.g., "Thabo Retirement Portfolio")
    private String name;

    // One-to-many relationship with Products - cascade all operations, eager fetch for API responses
    // JsonManagedReference prevents infinite recursion in JSON serialization
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Investor getInvestor() { return investor; }
    public void setInvestor(Investor i) { this.investor = i; }
    
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> p) { this.products = p; }
}
