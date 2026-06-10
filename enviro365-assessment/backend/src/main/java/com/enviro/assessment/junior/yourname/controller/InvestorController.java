package com.enviro.assessment.junior.yourname.controller;

import com.enviro.assessment.junior.yourname.model.Investor;
import com.enviro.assessment.junior.yourname.service.InvestorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
    private final InvestorService service;
    public InvestorController(InvestorService s) { this.service = s; }

    @GetMapping("/{id}")
    public Investor get(@PathVariable Long id) { return service.getById(id); }
}
