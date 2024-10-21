package com.github.danilobandeira29.easy_funds.entities;

public class UserPayeeStrategy implements IPayeeStrategy {
    private final User user;

    public UserPayeeStrategy(User u) {
        user = u;
    }

    @Override
    public String getDocument() {
        return user.cpf.getValue();
    }

    @Override
    public String getEmail() {
        return user.email.getValue();
    }

    @Override
    public PayeeEnum getType() {
        return PayeeEnum.COMMON;
    }

    @Override
    public String getName() {
        return user.fullName.getValue();
    }
}
