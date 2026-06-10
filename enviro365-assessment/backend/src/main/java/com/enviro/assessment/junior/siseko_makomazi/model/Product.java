package com.enviro.assessment.junior.siseko_makomazi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;

/**
 * Represents an investment product within a portfolio.
 * Products can be of type RETIREMENT or SAVINGS.
 * Type determines business rules for withdrawals (retirement requires age > 65).
 */
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Portfolio
    // JsonBackReference prevents infinite recursion in JSON serialization
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    @JsonBackReference
    private Portfolio portfolio;

    // Product type: RETIREMENT or SAVINGS
    // RETIREMENT products have age restrictions for withdrawals
    private String type;
    
    // Product name (e.g., "Pension Fund A", "Money Market")
    private String name;
    
    // Current balance available for withdrawal
    private BigDecimal balance;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio p) { this.portfolio = p; }
    
    public String getType() { return type; }
    public void setType(String t) { this.type = t; }
    
    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal b) { this.balance = b; }
}
