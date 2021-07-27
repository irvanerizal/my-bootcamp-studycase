package com.my.example.atm.service;

import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StageReviewNo7Test extends TestingProperties {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountService accountService;

    @Test
    void whenTransfer_andDestinationAccNotFound_thenReturnExpectedError() throws Exception {
        when(accountService.findAccount(account2.getAccountNumber()))
                .thenThrow(new UserNotFoundException("Account not found"));

        Assertions.assertThrows(UserNotFoundException.class, () ->
                transferService.transfer(account1, account2.getAccountNumber(), transferAmount5.toString(), refNumber));
    }

}