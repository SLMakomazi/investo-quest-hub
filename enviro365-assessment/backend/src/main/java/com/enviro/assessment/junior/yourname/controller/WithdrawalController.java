package com.enviro.assessment.junior.yourname.controller;

import com.enviro.assessment.junior.yourname.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.yourname.dto.WithdrawalResponseDTO;
import com.enviro.assessment.junior.yourname.model.Withdrawal;
import com.enviro.assessment.junior.yourname.service.WithdrawalService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    private final WithdrawalService service;
    public WithdrawalController(WithdrawalService s) { this.service = s; }

    @PostMapping
    public WithdrawalResponseDTO create(@Valid @RequestBody WithdrawalRequestDTO req) {
        return service.create(req);
    }

    @GetMapping("/investor/{investorId}")
    public List<Withdrawal> history(@PathVariable Long investorId) {
        return service.history(investorId);
    }

    @GetMapping("/export")
    public void export(@RequestParam Long investorId, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
            "attachment; filename=\"withdrawals-" + investorId + ".csv\"");
        PrintWriter w = response.getWriter();
        w.println("id,createdAt,productId,productName,amount");
        for (Withdrawal x : service.history(investorId)) {
            w.printf("%d,%s,%d,%s,%s%n",
                x.getId(), x.getCreatedAt(),
                x.getProduct().getId(), x.getProduct().getName(),
                x.getAmount());
        }
        w.flush();
    }
}
