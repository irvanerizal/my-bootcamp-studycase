package com.my.example.atm.service;

import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StageReviewNo6Test extends TestingProperties {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountService accountService;

    @Test
    void whenTransfer_andBalanceSenderIsNotEnough_thenReturnExpectedError() throws Exception {

        Mockito.lenient().when(accountService.findAccount(account2.getAccountNumber()))
                .thenReturn(account2);

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            transferService.transfer(accountWithZeroBalance, account2.getAccountNumber(), transferAmount5.toString(), refNumber);
        });
    }

}