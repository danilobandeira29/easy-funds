package com.github.danilobandeira29.easy_funds.controllers;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(
        @NotNull(message = "value must be float")
        @DecimalMin(value = "0.1", message = "value must be at least 0.1")
        BigDecimal value,
        @NotNull(message = "payer must be a valid uuid")
        UUID payer,
        @NotNull(message = "payee must be a valid uuid")
        UUID payee
){}
