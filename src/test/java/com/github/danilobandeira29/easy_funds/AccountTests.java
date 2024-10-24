package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.entities.Account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTests {

    @Test
    public void testAccountWithdrawToThrowsWhenBalanceIsLowerThanWithdraw() {
        var account = new Account(UUID.randomUUID(), new BigDecimal(10), UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(new BigDecimal(11));
        });
    }
    @Test
    public void testAccountSuccessfullyWithdrawOneHundredOfAccountWithBalanceOfOneHundred() {
        var account = new Account(UUID.randomUUID(), new BigDecimal(100), UUID.randomUUID());
        account.withdraw(new BigDecimal(50));
        account.withdraw(new BigDecimal(49));
        assertEquals(new BigDecimal(1), account.getBalance());
    }

    @Test
    public void testDepositSuccessfullyOneHundredTwoTimesInAccountWithBalanceOfOneHundred() {
        var account = new Account(UUID.randomUUID(), new BigDecimal(100), UUID.randomUUID());
        account.deposit(new BigDecimal(100));
        account.deposit(new BigDecimal(100));
        assertEquals(new BigDecimal(300), account.getBalance());
    }
}
