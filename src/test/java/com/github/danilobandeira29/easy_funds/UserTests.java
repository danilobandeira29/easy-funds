package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.entities.Account;
import com.github.danilobandeira29.easy_funds.entities.User;
import com.github.danilobandeira29.easy_funds.value_objects.CPF;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.FullName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    @Test
    public void testCpfMasked() {
        var fullName = new FullName("Danilo Bandeira");
        var cpf = new CPF("001.000.000-00");
        var account = new Account(new BigDecimal(0));
        var email = new Email("email@email.com");
        var password = "1234";
        var user = new User(UUID.randomUUID(), fullName, cpf, email, password, account);
        assertEquals("001.***.***-**", user.cpf.getWithMask());
    }

    @Test
    public void testLastName() {
        var fullName = new FullName("Danilo Bastos Bandeira");
        var cpf = new CPF("001.000.000-00");
        var account = new Account(new BigDecimal(0));
        var email = new Email("email@email.com");
        var password = "1234";
        var user = new User(UUID.randomUUID(), fullName, cpf, email, password, account);
        assertEquals("Bastos Bandeira", user.fullName.getLastName());
    }
}
