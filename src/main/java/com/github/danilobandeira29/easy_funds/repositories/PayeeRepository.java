package com.github.danilobandeira29.easy_funds.repositories;

import com.github.danilobandeira29.easy_funds.entities.*;
import com.github.danilobandeira29.easy_funds.value_objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PayeeRepository {
    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    IMerchantRepository merchantRepository;

    @Autowired
    IAccountsRepository accountUsersRepository;

    public Optional<IPayeeStrategy> findOneById(UUID id) {
        try {
            var merchantEntityOptional = merchantRepository.findById(id);
            var userEntityOptional = usersRepository.findById(id);
            if (userEntityOptional.isEmpty() && merchantEntityOptional.isEmpty()) {
                System.out.println("payeerepository: user and merchant not found");
                return Optional.empty();
            }
            var accountEntityOptional = accountUsersRepository.findByOwnerId(id);
            if (accountEntityOptional.isEmpty()) {
                System.out.println("payeerepository: account not found");
                return Optional.empty();
            }
            if (merchantEntityOptional.isPresent() && userEntityOptional.isEmpty()) {
                MerchantEntity merchantEntity = merchantEntityOptional.get();
                var razaoSocial = new RazaoSocial(merchantEntity.razaoSocial);
                var cnpj = new CNPJ(merchantEntity.cnpj);
                var email = new Email(merchantEntity.email);
                var password = merchantEntity.password;
                var account = new Account(accountEntityOptional.get().balance);
                var merchant = new Merchant(merchantEntity.id, razaoSocial, cnpj, email, password, account);
                var merchantStrategy = new MerchantPayeeStrategy(merchant);
                return Optional.of(merchantStrategy);
            }
            if (merchantEntityOptional.isEmpty()) {
                var userEntity = userEntityOptional.get();
                var fullName = new FullName(userEntity.fullName);
                var email = new Email(userEntity.email);
                var password = userEntity.password;
                var cpf = new CPF(userEntity.cpf);
                var account = new Account(accountEntityOptional.get().balance);
                var user = new User(userEntity.id, fullName, cpf, email, password, account);
                return Optional.of(new UserPayeeStrategy(user));
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("error when trying to find payee " + e);
            return Optional.empty();
        }
    }
}
