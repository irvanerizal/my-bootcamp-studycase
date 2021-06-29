package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.dao.repository.TransactionRepository;
import com.my.example.atm.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StageReviewNo10Test extends TestingProperties {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void whenBulkTransactionsSaved_thenGetTransactionByUserAccountIsSuccess_andReturnLatestTransactions() {

        List<Transaction> expectedResult = bulkTransactions.subList(0, querySize);
        Mockito.when(transactionRepository.findTransactionByCreatedBy(account1.getAccountNumber(), PageRequest.of(0, querySize)))
                .thenReturn(expectedResult);

        List<Transaction> result = transactionService.getTransactionByUserAccount(account1.getAccountNumber());
        Assertions.assertEquals(latestTenTransactions, result); //Proving sorting function
        Assertions.assertEquals(querySize, result.size());

        verify(transactionRepository, times(1)).findTransactionByCreatedBy(account1.getAccountNumber(), PageRequest.of(0, querySize));
    }
}