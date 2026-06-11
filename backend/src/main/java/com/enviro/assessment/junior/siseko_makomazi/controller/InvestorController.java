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
    // service is declared here as a final field and is assigned in the constructor.
    // It keeps controller code thin by delegating business lookup logic to InvestorService.
    private final InvestorService service;
    
    // s is declared as a constructor parameter; Spring injects the InvestorService bean here.
    public InvestorController(InvestorService s) { this.service = s; }

    /**
     * Get investor details by ID.
     * @param id The investor ID
     * @return Investor entity with all details
     */
    @GetMapping("/{id}")
    public Investor get(@PathVariable Long id) {
        // id is declared as a method parameter from the URL path variable.
        // The service returns the investor or throws ResourceNotFoundException.
        return service.getById(id);
    }
}
