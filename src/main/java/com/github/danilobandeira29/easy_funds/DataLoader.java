package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IAccountsRepository accountRepository;

    @Autowired
    private IMerchantRepository merchantRepository;

    @Override
    public void run(String... args) throws Exception {
        var isEmptyUsersRepo = usersRepository.count() == 0;
        var isEmptyAccountRepo = accountRepository.count() == 0;
        if (isEmptyAccountRepo || isEmptyUsersRepo) {
            seedData();
        }

    }

    private void seedData() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.id = UUID.fromString("1c0a69ff-bf1d-474d-a4ed-a9247e16ba0d");
            userEntity.cpf = "000.000.000-00";
            userEntity.email = "email@email.com";
            userEntity.fullName = "Danilo Bandeira";
            userEntity.password = "1234";
            System.out.println(userEntity);
            usersRepository.save(userEntity);
            UserEntity userEntity2 = new UserEntity();
            userEntity2.id = UUID.fromString("63015f60-9506-4b5d-ba29-8d7e944d634e");
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
            MerchantEntity merchantEntity = new MerchantEntity();
            merchantEntity.id = UUID.fromString("cfcb249c-62c5-41a2-9078-cb210f8c70a2");
            merchantEntity.cnpj = "01.000.000/0001-00";
            merchantEntity.email = "merchant@email.com";
            merchantEntity.razaoSocial = "Comercial Banana";
            merchantEntity.password = "1234";
            merchantRepository.save(merchantEntity);
            AccountEntity accountEntityMerchant = new AccountEntity();
            accountEntityMerchant.balance = new BigDecimal("1000.00");
            accountEntityMerchant.ownerId = merchantEntity.id;
            accountRepository.save(accountEntityMerchant);
        } catch (Exception e) {
            System.out.println("error when seeding " + e);
        }
    }
}