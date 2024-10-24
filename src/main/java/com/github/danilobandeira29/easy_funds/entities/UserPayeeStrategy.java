package com.github.danilobandeira29.easy_funds.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class UserPayeeStrategy implements IPayeeStrategy {
    private final User user;

    public UserPayeeStrategy(User u) {
        user = u;
    }

    @Override
    public UUID getId() {
        return user.getId();
    }

    @Override
    public String getDocument() {
        return user.getCpf().getValue();
    }

    @Override
    public String getEmail() {
        return user.getEmail().getValue();
    }

    @Override
    public PayeeEnum getType() {
        return PayeeEnum.COMMON;
    }

    @Override
    public String getName() {
        return user.getFullName().getValue();
    }

    @Override
    public void deposit(BigDecimal d) {
        user.getAccount().deposit(d);
    }

    @Override
    public Account getAccount() {
        return user.getAccount();
    }
}
