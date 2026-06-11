package com.enviro.assessment.junior.siseko_makomazi.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.junior.siseko_makomazi.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.siseko_makomazi.dto.WithdrawalResponseDTO;
import com.enviro.assessment.junior.siseko_makomazi.model.Withdrawal;
import com.enviro.assessment.junior.siseko_makomazi.service.WithdrawalService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * REST controller for withdrawal operations.
 * Provides endpoints to create withdrawals, view history, and export CSV statements.
 */
@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    private final WithdrawalService service;
    
    // Constructor injection of WithdrawalService
    public WithdrawalController(WithdrawalService s) { this.service = s; }

    /**
     * Create a new withdrawal request.
     * Validates the request against business rules before processing.
     * @param req The withdrawal request with investor ID, product ID, and amount
     * @return WithdrawalResponseDTO with withdrawal details and remaining balance
     */
    @PostMapping
    public WithdrawalResponseDTO create(@Valid @RequestBody WithdrawalRequestDTO req) {
        return service.create(req);
    }

    /**
     * Get withdrawal history for an investor.
     * Returns all withdrawals made by the specified investor.
     * @param investorId The investor ID
     * @return List of withdrawal entities
     */
    @GetMapping("/investor/{investorId}")
    public List<Withdrawal> history(@PathVariable Long investorId) {
        return service.history(investorId);
    }

    /**
     * Export withdrawal history as CSV file.
     * Generates a CSV download with withdrawal details for the specified investor.
     * Includes status for audit trail purposes.
     * @param investorId The investor ID to filter withdrawals
     * @param response HTTP response for CSV file download
     */
    @GetMapping("/export")
    public void export(@RequestParam Long investorId, HttpServletResponse response) throws IOException {
        // Set response headers for CSV download
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
            "attachment; filename=\"withdrawals-" + investorId + ".csv\"");
        
        // Write CSV header with status column
        PrintWriter w = response.getWriter();
        w.println("id,createdAt,productId,productName,amount,status");
        
        // Write withdrawal data rows including status
        for (Withdrawal x : service.history(investorId)) {
            w.printf("%d,%s,%d,%s,%s,%s%n",
                x.getId(), x.getCreatedAt(),
                x.getProduct().getId(), x.getProduct().getName(),
                x.getAmount(), x.getStatus());
        }
        w.flush();
    }
}
