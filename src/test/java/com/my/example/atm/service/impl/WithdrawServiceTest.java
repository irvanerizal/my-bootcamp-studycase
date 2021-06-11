package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.service.TestingProperties;
import com.my.example.atm.service.api.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawServiceTest extends TestingProperties {

    @InjectMocks
    WithdrawServiceImpl withdrawService;

    @Mock
    AccountServiceImpl accountService;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenWithdrawBalanceIsSuccess() throws Exception {
        doNothing().when(accountService).deductUserBalance(account1, withdrawAmount);
        doAnswer(invocation -> {
            Assertions.assertEquals(Transaction.Withdraw.class, invocation.getArgument(0).getClass());
            return null;
        }).when(transactionService).saveTransation(Mockito.any());

        withdrawService.withdraw(account1, withdrawAmount);

        verify(accountService, Mockito.times(1))
                .deductUserBalance(account1, withdrawAmount);
        verify(transactionService, Mockito.times(1))
                .saveTransation(Mockito.any());
    }

    @Test
    void whenWithdraw_andBalanceIsNotEnough_thenReturnExpectedError() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            withdrawService.withdraw(accountNotEnoughBalance, withdrawAmount);
        });
    }

}