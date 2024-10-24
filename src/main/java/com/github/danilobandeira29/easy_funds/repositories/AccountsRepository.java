package com.github.danilobandeira29.easy_funds.repositories;

import com.github.danilobandeira29.easy_funds.entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(rollbackOn = Exception.class)
    public void saveAllWithTransaction(Account from, Account to) {
        AccountEntity accountFrom = new AccountEntity();
        accountFrom.setId(from.getId());
        accountFrom.setBalance(from.getBalance());
        accountFrom.setOwnerId(from.getOwnerId());
        AccountEntity accountTo = new AccountEntity();
        accountTo.setId(to.getId());
        accountTo.setBalance(to.getBalance());
        accountTo.setOwnerId(to.getOwnerId());
        entityManager.merge(accountFrom);
        entityManager.merge(accountTo);
    }

}
