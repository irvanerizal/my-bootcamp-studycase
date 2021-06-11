package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.service.TestingProperties;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest extends TestingProperties {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Test
    void whenTransferIsSuccess() throws Exception {

        doNothing().when(accountService).deductUserBalance(account1, transferAmount);
        doNothing().when(accountService).addUserBalance(account2, transferAmount);

        doAnswer(invocation -> {
            Assertions.assertEquals(Transaction.Transfer.class, invocation.getArgument(0).getClass());
            return null;
        }).when(transactionService).saveTransation(Mockito.any());

        when(accountService.findAccount(account2.getAccountNumber()))
                .thenReturn(account2);

        transferService.transfer(account1, account2.getAccountNumber(), transferAmount.toString(), refNumber);

        verify(accountService, Mockito.times(1))
                .deductUserBalance(account1, transferAmount);
        verify(accountService, Mockito.times(1))
                .addUserBalance(account2, transferAmount);
        verify(transactionService, Mockito.times(2))
                .saveTransation(Mockito.any());
    }

    @Test
    void whenTransfer_andBalanceSenderIsNotEnough_thenReturnExpectedError() throws Exception {

        Mockito.lenient().when(accountService.findAccount(account2.getAccountNumber()))
                .thenReturn(account2);

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            transferService.transfer(accountNotEnoughBalance, account2.getAccountNumber(), transferAmount.toString(), refNumber);
        });
    }

    @Test
    void whenTransfer_andDestinationAccNotFound_thenReturnExpectedError() throws Exception {
        when(accountService.findAccount(account2.getAccountNumber()))
                .thenThrow(new UserNotFoundException("Account not found"));

        Assertions.assertThrows(UserNotFoundException.class, () ->
                transferService.transfer(account1, account2.getAccountNumber(), transferAmount.toString(), refNumber));
    }

}