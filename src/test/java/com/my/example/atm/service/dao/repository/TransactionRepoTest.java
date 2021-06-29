package com.my.example.atm.service.dao.repository;


import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.dao.repository.TransactionRepository;
import com.my.example.atm.service.TestingProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
public class TransactionRepoTest extends TestingProperties {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository.saveAll(latestTenTransactions);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void whenfindByCreatedBy(){
        List<Transaction> result = transactionRepository.findTransactionByCreatedBy(account1.getAccountNumber(), PageRequest.of(0, 10));
        Assertions.assertEquals(latestTenTransactions.size(), result.size());
    }

}
