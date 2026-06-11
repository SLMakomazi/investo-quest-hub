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

    // service is declared as a final field and assigned in the constructor.
    // It contains the actual withdrawal validation, saving, and history logic.
    private final WithdrawalService service;
    
    // s is declared as a constructor parameter; Spring injects WithdrawalService here.
    public WithdrawalController(WithdrawalService s) { this.service = s; }

    /**
     * Create a new withdrawal request.
     * Validates the request against business rules before processing.
     * @param req The withdrawal request with investor ID, product ID, and amount
     * @return WithdrawalResponseDTO with withdrawal details and remaining balance
     */
    @PostMapping
    public WithdrawalResponseDTO create(@Valid @RequestBody WithdrawalRequestDTO req) {
        // req is declared as the request body parameter.
        // @Valid tells Spring to check required fields before this method continues.
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
        // investorId is declared from the URL path and is used to filter history records.
        return service.history(investorId);
    }

    /**
     * Export withdrawal history as CSV file.
     * Generates a CSV download with withdrawal details for the specified investor.
     * Includes status and rejection reason for audit trail purposes.
     * @param investorId The investor ID to filter withdrawals
     * @param response HTTP response for CSV file download
     */
    @GetMapping("/export")
    public void export(@RequestParam Long investorId, HttpServletResponse response) throws IOException {
        // investorId is declared as a query parameter, for example ?investorId=1.
        // response is declared by Spring and lets us write the CSV directly to the HTTP response.
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
            "attachment; filename=\"withdrawals-" + investorId + ".csv\"");
        
        // w is declared here as the writer used to send CSV text back to the browser.
        PrintWriter w = response.getWriter();
        w.println("id,createdAt,productId,productName,amount,status,rejectionReason");
        
        // x is declared inside the loop and represents one withdrawal history row.
        for (Withdrawal x : service.history(investorId)) {
            w.printf("%d,%s,%d,%s,%s,%s,%s%n",
                x.getId(), x.getCreatedAt(),
                x.getProduct().getId(), x.getProduct().getName(),
                x.getAmount(), x.getStatus(), 
                // If rejectionReason is null, write an empty CSV value so approved rows stay clean.
                x.getRejectionReason() != null ? x.getRejectionReason() : "");
        }
        // flush forces any buffered CSV content to be sent before the method ends.
        w.flush();
    }
}
