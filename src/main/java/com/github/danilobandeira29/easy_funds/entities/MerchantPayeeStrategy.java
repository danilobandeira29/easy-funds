package com.github.danilobandeira29.easy_funds.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class MerchantPayeeStrategy implements IPayeeStrategy {
    private final Merchant merchant;

    public MerchantPayeeStrategy(Merchant m) {
        merchant = m;
    }

    @Override
    public UUID getId() {
        return merchant.id;
    }

    @Override
    public String getDocument() {
        return merchant.cnpj.getValue();
    }

    @Override
    public String getEmail() {
        return merchant.email.getValue();
    }

    @Override
    public PayeeEnum getType() {
        return PayeeEnum.MERCHANT;
    }

    @Override
    public String getName() {
        return merchant.razaoSocial.getValue();
    }

    @Override
    public void deposit(BigDecimal d) {
        merchant.account.deposit(d);
    }

    @Override
    public Account getAccount() {
        return merchant.account;
    }
}
