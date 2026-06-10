package com.enviro.assessment.junior.yourname.controller;

import com.enviro.assessment.junior.yourname.model.Portfolio;
import com.enviro.assessment.junior.yourname.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    private final PortfolioService service;
    public PortfolioController(PortfolioService s) { this.service = s; }

    @GetMapping("/investor/{investorId}")
    public Portfolio byInvestor(@PathVariable Long investorId) {
        return service.getByInvestorId(investorId);
    }
}
