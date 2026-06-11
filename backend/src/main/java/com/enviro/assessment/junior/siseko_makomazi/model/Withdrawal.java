package com.enviro.assessment.junior.siseko_makomazi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a withdrawal request made by an investor from a specific product.
 * Records the amount withdrawn, the investor, product, timestamp, and status.
 * Used for withdrawal history, audit trail, and CSV export functionality.
 */
@Entity
public class Withdrawal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The investor who made the withdrawal
    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    // The product from which the withdrawal was made
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // The amount withdrawn (validated against business rules)
    private BigDecimal amount;
    
    // Timestamp of when the withdrawal was created (auto-set on creation)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Withdrawal status: APPROVED, REJECTED, or PENDING
    // Default is APPROVED since only successful withdrawals are saved
    private String status = "APPROVED";

    // Reason for rejection (only populated when status is REJECTED)
    private String rejectionReason;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Investor getInvestor() { return investor; }
    public void setInvestor(Investor i) { this.investor = i; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product p) { this.product = p; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    
    public String getStatus() { return status; }
    public void setStatus(String s) { this.status = s; }
    
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String r) { this.rejectionReason = r; }
}
