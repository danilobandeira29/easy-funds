package com.github.danilobandeira29.easy_funds;

import com.github.danilobandeira29.easy_funds.controllers.ApiResponseDto;
import com.github.danilobandeira29.easy_funds.controllers.TransferRequestDto;
import com.github.danilobandeira29.easy_funds.controllers.TransferResponseDto;
import com.github.danilobandeira29.easy_funds.repositories.AccountsRepository;
import com.github.danilobandeira29.easy_funds.repositories.PayeeRepository;
import com.github.danilobandeira29.easy_funds.repositories.UsersRepository;
import com.github.danilobandeira29.easy_funds.services.IEventPublisher;
import com.github.danilobandeira29.easy_funds.services.TransferErrorCodesEnum;
import com.github.danilobandeira29.easy_funds.services.TransferService;
import com.github.danilobandeira29.easy_funds.transfer_authorization.IAuthorizationAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class TransferServiceIntegrationTest {

    @Autowired
    private TransferService transferService;

    @MockBean
    private IAuthorizationAdapter authorizationAdapter;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @MockBean
    private IEventPublisher eventPublisher;

    @Test
    void execute_transferAuthorized_shouldWithdrawPayerAccountAndDepositIntoPayeeAccountAndTransferBeAuthorized() {
        UUID payerId = UUID.fromString("1c0a69ff-bf1d-474d-a4ed-a9247e16ba0d");
        UUID payeeId = UUID.fromString("cfcb249c-62c5-41a2-9078-cb210f8c70a2");
        BigDecimal transferAmount = BigDecimal.valueOf(100);
        TransferRequestDto transferRequestDto = new TransferRequestDto(transferAmount, payerId, payeeId);
        when(authorizationAdapter.isAuthorized()).thenReturn(true);

        var payer = usersRepository.findUserWithAccountById(payerId);
        assertEquals(new BigDecimal(1000), payer.get().getAccount().getBalance());
        var payee = payeeRepository.findOneById(payeeId);
        assertEquals(new BigDecimal(1000), payee.get().getAccount().getBalance());

        ApiResponseDto<?> response = transferService.execute(transferRequestDto);

        assertTrue(response.isSuccess());
        TransferResponseDto transferResponseDto = (TransferResponseDto) response.getData();
        assertEquals(transferAmount, transferResponseDto.getAmount());
        assertEquals(payerId, transferResponseDto.getPayerId());
        assertEquals(payeeId, transferResponseDto.getPayeeId());
        payer = usersRepository.findUserWithAccountById(payerId);
        assertEquals(new BigDecimal(900), payer.get().getAccount().getBalance());
        payee = payeeRepository.findOneById(payeeId);
        assertEquals(new BigDecimal(1100), payee.get().getAccount().getBalance());
    }

    @Test
    void execute_transferNotAuthorized_shouldNotWithdrawPayerAccountAndNotDepositIntoPayeeAccountBecauseTransferIsUnauthorized() {
        UUID payerId = UUID.fromString("1c0a69ff-bf1d-474d-a4ed-a9247e16ba0d");
        UUID payeeId = UUID.fromString("cfcb249c-62c5-41a2-9078-cb210f8c70a2");
        BigDecimal transferAmount = BigDecimal.valueOf(100);
        TransferRequestDto transferRequestDto = new TransferRequestDto(transferAmount, payerId, payeeId);
        when(authorizationAdapter.isAuthorized()).thenReturn(false);

        var payer = usersRepository.findUserWithAccountById(payerId);
        assertEquals(new BigDecimal(1000), payer.get().getAccount().getBalance());
        var payee = payeeRepository.findOneById(payeeId);
        assertEquals(new BigDecimal(1000), payee.get().getAccount().getBalance());

        ApiResponseDto<?> response = transferService.execute(transferRequestDto);

        assertFalse(response.isSuccess());
        assertTrue(response.codeEquals(TransferErrorCodesEnum.TRANSFER_UNAUTHORIZED.toString()));
        payer = usersRepository.findUserWithAccountById(payerId);
        assertEquals(new BigDecimal(1000), payer.get().getAccount().getBalance());
        payee = payeeRepository.findOneById(payeeId);
        assertEquals(new BigDecimal(1000), payee.get().getAccount().getBalance());
    }
}
