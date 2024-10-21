package com.github.danilobandeira29.easy_funds.entities;

import com.github.danilobandeira29.easy_funds.value_objects.CPF;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.FullName;

import java.util.UUID;

public class User {
    public final UUID id;
    public final FullName fullName;
    public final CPF cpf;
    public final Email email;
    public final String password;
    public final Account account;

    public User(UUID i, FullName f, CPF c, Email e, String p,Account a) {
        id = i;
        fullName = f;
        cpf = c;
        account = a;
        email = e;
        password = p;
    }

}
