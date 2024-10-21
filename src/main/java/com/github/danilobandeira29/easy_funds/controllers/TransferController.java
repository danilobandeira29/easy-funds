package com.github.danilobandeira29.easy_funds.controllers;

import com.github.danilobandeira29.easy_funds.repositories.PayeeRepository;
import com.github.danilobandeira29.easy_funds.repositories.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PayeeRepository payeeRepository;
    // todo
    // service to transfer, save into the database
    // publish transf event
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferDto transferDto) {
        if(transferDto.payee().equals(transferDto.payer())) {
            return ResponseEntity.badRequest().build();
        }
        var payer = usersRepository.findUserWithAccountById(transferDto.payer());
        if (payer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var payee = payeeRepository.findOneById(transferDto.payee());
        if (payee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var user = payer.get();
        System.out.println(user);
        System.out.println(payee.get());
        return ResponseEntity.noContent().build();
    }
}
