package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.entities.Account;
import com.github.danilobandeira29.easy_funds.entities.Merchant;
import com.github.danilobandeira29.easy_funds.value_objects.CNPJ;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.RazaoSocial;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class MerchantTests {

    @Test
    public void testCNPJWithMask() {
        var razaoSocial = new RazaoSocial("Comercio do Paulo");
        var cnpj = new CNPJ("00.000.000/0001-00");
        var account = new Account(new BigDecimal(0));
        var email = new Email("email@email.com");
        var password = "1234";
        var merchant = new Merchant(razaoSocial, cnpj, email, password, account);
        assertEquals("00.***.***/****-**", merchant.cnpj.getWithMask());
    }
}
