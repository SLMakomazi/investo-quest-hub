package com.enviro.assessment.junior.yourname.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalResponseDTO {
    public Long id;
    public Long investorId;
    public Long productId;
    public String productName;
    public BigDecimal amount;
    public BigDecimal remainingBalance;
    public LocalDateTime createdAt;
}
