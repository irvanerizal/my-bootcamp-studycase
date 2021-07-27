package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.repository.AccountRepository;
import com.my.example.atm.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StageReviewNo3Test extends TestingProperties {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void whenDeductUserBalanceIsSuccess_ThenBalanceShouldDecreased() {
        when(accountRepository.findByAccountNumber(account1.getAccountNumber()))
                .thenReturn(account1);

        doAnswer(invocation -> {
            Account result = invocation.getArgument(0);
            Assertions.assertEquals(90, result.getBalance());
            return null;
        }).when(accountRepository).save(Mockito.any());

        accountService.deductUserBalance(account1, withdrawAmount10);

        verify(accountRepository, Mockito.times(1))
                .findByAccountNumber(account1.getAccountNumber());
    }
}