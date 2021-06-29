package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.repository.AccountRepository;
import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.service.TestingProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends TestingProperties {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void whenValidateAccountIsSuccess() throws Exception {
        when(accountRepository.findByAccountNumber(account1.getAccountNumber()))
                .thenReturn(account1);

        Account result = accountService.findAccount(account1.getAccountNumber());
        Assertions.assertEquals(account1, result);

        verify(accountRepository, times(1)).findByAccountNumber(account1.getAccountNumber());
    }

    @Test
    void whenValidateAccount_andAccountNotFound_thenReturnExpectedError() {
        Assertions.assertThrows(UserNotFoundException.class, () -> accountService.findAccount(account1.getAccountNumber()));
    }

    @Test
    void whenFindAccountIsSuccess() throws Exception {
        when(accountRepository.findByAccountNumberAndPin(account1.getAccountNumber(), account1.getPin()))
                .thenReturn(account1);

        Account result = accountService.validateAccount(account1.getAccountNumber(), account1.getPin());
        Assertions.assertEquals(account1, result);

        verify(accountRepository, times(1)).findByAccountNumberAndPin(account1.getAccountNumber(), account1.getPin());
    }

    @Test
    void whenFindAccount_andAccountNotFound_thenReturnExpectedError() {
        Assertions.assertThrows(UserNotFoundException.class, () -> accountService.validateAccount(account1.getAccountNumber(), account1.getPin()));
    }

    // TODO: Unit Test Case Point 3, 5
    @Test
    void whenDeductUserBalanceIsSuccess_ThenBalanceShouldDecreased() {
        when(accountRepository.findByAccountNumber(account1.getAccountNumber()))
                .thenReturn(account1);

        doAnswer(invocation -> {
            Account result = invocation.getArgument(0);
            Assertions.assertEquals(90, result.getBalance());
            return null;
        }).when(accountRepository).save(Mockito.any());

        accountService.deductUserBalance(account1, withdrawAmount);

        verify(accountRepository, Mockito.times(1))
                .findByAccountNumber(account1.getAccountNumber());
    }

    // TODO: Unit Test Case Point 5
    @Test
    void whenAddUserBalanceIsSuccess_ThenBalanceShouldIncreased() {
        when(accountRepository.findByAccountNumber(account1.getAccountNumber()))
                .thenReturn(account1);
        doAnswer(invocation -> {
            Account result = invocation.getArgument(0);
            Assertions.assertEquals(110, result.getBalance());
            return null;

        }).when(accountRepository).save(Mockito.any());

        accountService.addUserBalance(account1, withdrawAmount);

        verify(accountRepository, Mockito.times(1))
                .findByAccountNumber(account1.getAccountNumber());
    }
}