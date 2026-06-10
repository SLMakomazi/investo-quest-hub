package com.enviro.assessment.junior.yourname.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class WithdrawalRequestDTO {
    @NotNull
    public Long investorId;
    @NotNull
    public Long productId;
    @NotNull @Positive
    public BigDecimal amount;
}
