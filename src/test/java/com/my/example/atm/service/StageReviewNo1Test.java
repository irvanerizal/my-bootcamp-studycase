package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(connection= EmbeddedDatabaseConnection.H2)
@DataJpaTest
public class StageReviewNo1Test extends TestingProperties {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.save(account1);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void whenFindByAccountNumberAndPinIsSuccess(){
        Account result = accountRepository.findByAccountNumberAndPin(account1.getAccountNumber(), account1.getPin());
        Assertions.assertEquals(account1, result);
    }

}
