package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.entities.Account;
import com.github.danilobandeira29.easy_funds.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IAccountRepository accountRepository
            ;
    @Override
    public void run(String... args) throws Exception {
        var isEmptyUsersRepo = usersRepository.count() == 0;
        var isEmptyAccountRepo = accountRepository.count() == 0;
        if (isEmptyAccountRepo || isEmptyUsersRepo) {
            seedData();
        }

    }

    private void seedData() {
        UserEntity userEntity = new UserEntity();
        userEntity.cpf = "000.000.000-00";
        userEntity.email = "email@email.com";
        userEntity.fullName = "Danilo Bandeira";
        userEntity.password = "1234";
        System.out.println(userEntity);
        usersRepository.save(userEntity);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.cpf = "001.000.000-00";
        userEntity2.email = "email2@email.com";
        userEntity2.fullName = "Ana Banana";
        userEntity2.password = "1234";
        usersRepository.save(userEntity2);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.balance = new BigDecimal("1000.00");
        accountEntity.ownerId = userEntity.id;
        accountRepository.save(accountEntity);
        AccountEntity accountEntity2 = new AccountEntity();
        accountEntity2.balance = new BigDecimal("1000.00");
        accountEntity2.ownerId = userEntity2.id;
        accountRepository.save(accountEntity2);
    }
}
