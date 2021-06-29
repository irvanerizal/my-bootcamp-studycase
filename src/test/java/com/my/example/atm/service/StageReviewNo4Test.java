package com.my.example.atm.service;

import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.service.api.TransactionService;
import com.my.example.atm.service.impl.AccountServiceImpl;
import com.my.example.atm.service.impl.WithdrawServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StageReviewNo4Test extends TestingProperties {

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
    void whenWithdraw_andBalanceIsNotEnough_thenReturnExpectedError() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            withdrawService.withdraw(accountNotEnoughBalance, withdrawAmount);
        });
    }

}