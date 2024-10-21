package com.github.danilobandeira29.easy_funds.controllers;

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
    // todo
    // service to transfer, save into the database
    // publish transf event
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferDto transferDto) {
        var payer = usersRepository.findUserWithAccountById(transferDto.payer());
        if (payer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payer.get());
    }
}
