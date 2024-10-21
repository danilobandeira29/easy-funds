package com.github.danilobandeira29.easy_funds.entities;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance;

    public Account(BigDecimal b) {
        balance = b;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal b) throws IllegalArgumentException {
        if (b.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("account: the value to deposit must be greater than zero");
        }
        balance = balance.add(b);
    }

    public void withdraw(BigDecimal b) throws IllegalArgumentException {
        if (balance.compareTo(b) < 0) {
            throw new IllegalArgumentException("account: insufficient balance");
        }
        balance = balance.subtract(b);
    }
}
