package com.github.danilobandeira29.easy_funds.entities;

public class MerchantPayeeStrategy implements IPayeeStrategy {
    private final Merchant merchant;

    public MerchantPayeeStrategy(Merchant m) {
        merchant = m;
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
}
