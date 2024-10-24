package com.github.danilobandeira29.easy_funds.controllers;

import com.github.danilobandeira29.easy_funds.repositories.AccountsRepository;
import com.github.danilobandeira29.easy_funds.repositories.PayeeRepository;
import com.github.danilobandeira29.easy_funds.repositories.UsersRepository;
import com.github.danilobandeira29.easy_funds.transfer_authorization.IAuthorizationAdapter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private IAuthorizationAdapter authorizationAdapter;
    // todo
    // [ ] publish transf event
    @PostMapping("/transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transfer done with success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(name = "TRANSFER_SUCCESS", value = "{\"status\":\"success\",\"data\":{\"amount\":10,\"payerId\":\"1c0a69ff-bf1d-474d-a4ed-a9247e16ba0d\",\"payeeId\":\"63015f60-9506-4b5d-ba29-8d7e944d634e\"},\"error\":null,\"code\":null}"))),
            @ApiResponse(responseCode = "404", description = "payer or payee not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = {
                            @ExampleObject(name = "TRANSFER_PAYER_NOT_FOUND", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"payer not found\",\"code\":\"TRANSFER_PAYER_NOT_FOUND\"}"),
                            @ExampleObject(name = "TRANSFER_PAYEE_NOT_FOUND", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"payee not found\",\"code\":\"TRANSFER_PAYEE_NOT_FOUND\"}")
                    })),
            @ApiResponse(responseCode = "400", description = "insufficient balance",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                            examples = {
                                    @ExampleObject(name = "TRANSFER_SELF_TRANSFER", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"cannot transfer to yourself\",\"code\":\"TRANSFER_SELF_TRANSFER\"}"),
                                    @ExampleObject(name = "TRANSFER_PAYER_INSUFFICIENT_BALANCE", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"insufficient balance\",\"code\":\"TRANSFER_PAYER_INSUFFICIENT_BALANCE\"}"),
                                    @ExampleObject(name = "TRANSFER_UNAUTHORIZED", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"transfer unauthorized\",\"code\":\"TRANSFER_UNAUTHORIZED\"}")
                            })),
            @ApiResponse(responseCode = "500", description = "authorization/internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class),
                    examples = {
                            @ExampleObject(name = "TRANSFER_AUTHORIZATION", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_AUTHORIZATION\"}"),
                            @ExampleObject(name = "TRANSFER_AUTHORIZATION_ERROR", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_AUTHORIZATION_ERROR\"}"),
                            @ExampleObject(name = "TRANSFER_PERSISTENCE_ERROR", value = "{\"status\":\"fail\",\"data\":null,\"error\":\"internal server error\",\"code\":\"TRANSFER_PERSISTENCE_ERROR\"}")
                    }))
    })
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequestDto transferDto) {
        if(transferDto.payee().equals(transferDto.payer())) {
            return ResponseEntity.badRequest().body(ApiResponseDto.fail("cannot transfer to yourself", "TRANSFER_SELF_TRANSFER"));
        }
        var payer = usersRepository.findUserWithAccountById(transferDto.payer());
        if (payer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.fail("payer not found", "TRANSFER_PAYER_NOT_FOUND"));
        }
        var payeeOpt = payeeRepository.findOneById(transferDto.payee());
        if (payeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.fail("payee not found", "TRANSFER_PAYEE_NOT_FOUND"));
        }
        var payee = payeeOpt.get();
        var user = payer.get();
        try {
            user.getAccount().withdraw(transferDto.value());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.fail("insufficient balance", "TRANSFER_PAYER_INSUFFICIENT_BALANCE"));
        }
        if (!authorizationAdapter.isAuthorized()) {
            return ResponseEntity.badRequest().body(ApiResponseDto.fail("transfer unauthorized", "TRANSFER_UNAUTHORIZED"));
        }
        payee.deposit(transferDto.value());
        try {
            accountsRepository.saveAllWithTransaction(user.getAccount(), payee.getAccount());
        } catch (Exception e) {
            System.out.println("exception occurred when trying to save accounts into database " + e);
            return ResponseEntity.internalServerError().body(ApiResponseDto.fail("internal server error", "TRANSFER_PERSISTENCE_ERROR"));
        }
        TransferResponseDto dto = new TransferResponseDto(transferDto.value(), user.getId(), payee.getId());
        return ResponseEntity.ok().body(ApiResponseDto.success(dto));
    }
}
