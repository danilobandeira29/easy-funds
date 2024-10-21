package com.github.danilobandeira29.easy_funds.entities;

import com.github.danilobandeira29.easy_funds.value_objects.CNPJ;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.RazaoSocial;

public class Merchant {
    public RazaoSocial razaoSocial;
    public CNPJ cnpj;
    public final Account account;
    public final Email email;
    public final String password;

    public Merchant(RazaoSocial r, CNPJ c, Email e, String p, Account a) {
        razaoSocial = r;
        cnpj = c;
        account = a;
        email = e;
        password = p;
    }
}
