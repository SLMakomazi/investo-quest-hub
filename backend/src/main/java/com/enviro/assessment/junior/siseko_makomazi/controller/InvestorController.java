package com.enviro.assessment.junior.siseko_makomazi.controller;

import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.junior.siseko_makomazi.model.Investor;
import com.enviro.assessment.junior.siseko_makomazi.service.InvestorService;

/**
 * REST controller for investor-related operations.
 * Provides endpoints to retrieve investor information.
 */
@RestController
@RequestMapping("/api/investors")
public class InvestorController {
    private final InvestorService service;
    
    // Constructor injection of InvestorService
    public InvestorController(InvestorService s) { this.service = s; }

    /**
     * Get investor details by ID.
     * @param id The investor ID
     * @return Investor entity with all details
     */
    @GetMapping("/{id}")
    public Investor get(@PathVariable Long id) { return service.getById(id); }
}
