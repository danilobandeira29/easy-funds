package com.github.danilobandeira29.easy_funds.entities;

import com.github.danilobandeira29.easy_funds.value_objects.CPF;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.FullName;

import java.util.UUID;

public class User {
    private final UUID id;
    private final FullName fullName;
    private final CPF cpf;
    private final Email email;
    private final String password;
    private final Account account;

    public FullName getFullName() {
        return fullName;
    }

    public CPF getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount() {
        return account;
    }

    public UUID getId() {
        return id;
    }

    public User(UUID i, FullName f, CPF c, Email e, String p, Account a) {
        id = i;
        fullName = f;
        cpf = c;
        account = a;
        email = e;
        password = p;
    }

}
