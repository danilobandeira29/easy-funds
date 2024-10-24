package com.github.danilobandeira29.easy_funds.entities;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPayeeStrategy {
    UUID getId();
    String getDocument();
    String getEmail();
    PayeeEnum getType();
    String getName();
    void deposit(BigDecimal b);
    Account getAccount();
}
