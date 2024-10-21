package com.github.danilobandeira29.easy_funds.controllers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferDto (
        @NotNull(message = "value must be float")
        @Positive(message = "value must be greater than zero")
        BigDecimal value,
        @NotNull(message = "payer must be a integer")
        Long payer,
        @NotNull(message = "payee must be a integer")
        Long payee
){}
