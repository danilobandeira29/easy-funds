package com.github.danilobandeira29.easy_funds.controllers;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class TransferResponseDto {
    private BigDecimal amount;
    private UUID payerId;
    private UUID payeeId;

    public TransferResponseDto(BigDecimal amount, UUID payerId, UUID payeeId) {
        this.amount = amount;
        this.payerId = payerId;
        this.payeeId = payeeId;
    }
}
