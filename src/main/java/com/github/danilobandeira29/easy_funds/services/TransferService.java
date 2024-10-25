package com.github.danilobandeira29.easy_funds.services;

import com.github.danilobandeira29.easy_funds.controllers.ApiResponseDto;
import com.github.danilobandeira29.easy_funds.controllers.TransferRequestDto;
import com.github.danilobandeira29.easy_funds.controllers.TransferResponseDto;
import com.github.danilobandeira29.easy_funds.repositories.AccountsRepository;
import com.github.danilobandeira29.easy_funds.repositories.PayeeRepository;
import com.github.danilobandeira29.easy_funds.repositories.UsersRepository;
import com.github.danilobandeira29.easy_funds.transfer_authorization.IAuthorizationAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private IAuthorizationAdapter authorizationAdapter;

    public ApiResponseDto<?> execute(TransferRequestDto transferDto) {
        if (transferDto.payee().equals(transferDto.payer())) {
            return ApiResponseDto.fail("cannot transfer to yourself", "TRANSFER_SELF_TRANSFER");
        }
        var payer = usersRepository.findUserWithAccountById(transferDto.payer());
        if (payer.isEmpty()) {
            return ApiResponseDto.fail("payer not found", "TRANSFER_PAYER_NOT_FOUND");
        }
        var payeeOpt = payeeRepository.findOneById(transferDto.payee());
        if (payeeOpt.isEmpty()) {
            return ApiResponseDto.fail("payee not found", "TRANSFER_PAYEE_NOT_FOUND");
        }
        var payee = payeeOpt.get();
        var user = payer.get();
        try {
            user.getAccount().withdraw(transferDto.value());
        } catch (IllegalArgumentException e) {
            return ApiResponseDto.fail("insufficient balance", "TRANSFER_PAYER_INSUFFICIENT_BALANCE");
        }
        if (!authorizationAdapter.isAuthorized()) {
            return ApiResponseDto.fail("transfer unauthorized", "TRANSFER_UNAUTHORIZED");
        }
        try {
            payee.deposit(transferDto.value());
        } catch (Exception e) {
            return ApiResponseDto.fail("cannot deposit negative value", "TRANSFER_DEPOSIT_NEGATIVE_VALUE_FOR_PAYEE");
        }
        try {
            accountsRepository.saveAllWithTransaction(user.getAccount(), payee.getAccount());
        } catch (Exception e) {
            System.out.println("transferservice: exception occurred when trying to save accounts into database " + e);
            return ApiResponseDto.fail("internal server error", "TRANSFER_PERSISTENCE_ERROR");
        }
        TransferResponseDto dto = new TransferResponseDto(transferDto.value(), user.getId(), payee.getId());
        return ApiResponseDto.success(dto);
    }
}
