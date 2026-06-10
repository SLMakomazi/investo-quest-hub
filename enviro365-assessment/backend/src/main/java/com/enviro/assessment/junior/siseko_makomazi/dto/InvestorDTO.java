package com.enviro.assessment.junior.siseko_makomazi.dto;

/**
 * Data Transfer Object for Investor information.
 * Used to send investor data to the frontend without exposing internal entity structure.
 */
public class InvestorDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public int age;
}
