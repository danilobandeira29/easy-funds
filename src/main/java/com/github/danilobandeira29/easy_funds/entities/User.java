package com.github.danilobandeira29.easy_funds.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danilobandeira29.easy_funds.value_objects.CPF;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.FullName;

public class User {
    @JsonProperty("fullName")
    public final FullName fullName;
    @JsonProperty("cpf")
    public final CPF cpf;
    @JsonProperty("email")
    public final Email email;
    public final String password;
    @JsonProperty("account")
    public final Account account;

    public User(FullName f, CPF c, Email e, String p, Account a) {
        fullName = f;
        cpf = c;
        account = a;
        email = e;
        password = p;
    }

}
