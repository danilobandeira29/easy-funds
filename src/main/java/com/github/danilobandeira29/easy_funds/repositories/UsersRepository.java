package com.github.danilobandeira29.easy_funds.repositories;

import com.github.danilobandeira29.easy_funds.entities.Account;
import com.github.danilobandeira29.easy_funds.entities.User;
import com.github.danilobandeira29.easy_funds.value_objects.CPF;
import com.github.danilobandeira29.easy_funds.value_objects.Email;
import com.github.danilobandeira29.easy_funds.value_objects.FullName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepository {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IAccountRepository accountRepository;

    public Optional<User> findUserWithAccountById(Long id) {
        try {
            var u = usersRepository.findById(id);
            var accountEntity = accountRepository.findByOwnerId(id);
            if (u.isEmpty() || accountEntity.isEmpty()) {
                return Optional.empty();
            }
            var userEntity = u.get();
            var fullName = new FullName(userEntity.fullName);
            var email = new Email(userEntity.email);
            var password = userEntity.password;
            var cpf = new CPF(userEntity.cpf);
            var account = new Account(accountEntity.get().balance);
            return Optional.of(new User(fullName, cpf, email, password, account));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
