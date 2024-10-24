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
        var id = UUID.randomUUID();
        var account = new Account(UUID.randomUUID(), new BigDecimal(0), id);
        var email = new Email("email@email.com");
        var password = "1234";
        var user = new User(id, fullName, cpf, email, password, account);
        assertEquals("001.***.***-**", user.getCpf().getWithMask());
    }

    @Test
    public void testLastName() {
        var fullName = new FullName("Danilo Bastos Bandeira");
        var cpf = new CPF("001.000.000-00");
        var id = UUID.randomUUID();
        var account = new Account(UUID.randomUUID(), new BigDecimal(0), id);
        var email = new Email("email@email.com");
        var password = "1234";
        var user = new User(id, fullName, cpf, email, password, account);
        assertEquals("Bastos Bandeira", user.getFullName().getLastName());
    }
}
