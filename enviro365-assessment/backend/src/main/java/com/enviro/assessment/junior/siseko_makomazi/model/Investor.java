package com.enviro.assessment.junior.siseko_makomazi.model;

import jakarta.persistence.*;

/**
 * Represents an investor in the system.
 * Contains personal information including name, email, and age.
 * Age is important for business rule validation (retirement withdrawals require age > 65).
 */
@Entity
public class Investor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    
    // Age is critical for business rules - retirement products require age > 65
    private int age;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }
    
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    
    public int getAge() { return age; }
    public void setAge(int v) { this.age = v; }
}
