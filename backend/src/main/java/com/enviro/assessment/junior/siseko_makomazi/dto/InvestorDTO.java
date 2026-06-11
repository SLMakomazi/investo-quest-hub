package com.enviro.assessment.junior.siseko_makomazi.dto;

/**
 * Data Transfer Object for Investor information.
 * Used to send investor data to the frontend without exposing internal entity structure.
 */
public class InvestorDTO {
    // id is declared here as the investor's unique database identifier.
    public Long id;
    // firstName is declared here as the investor's given name for display.
    public String firstName;
    // lastName is declared here as the investor's surname for display.
    public String lastName;
    // email is declared here as the investor's contact email.
    public String email;
    // age is declared here because retirement withdrawal validation depends on it.
    public int age;
}
