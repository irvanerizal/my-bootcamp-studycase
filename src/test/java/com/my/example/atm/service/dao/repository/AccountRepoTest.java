package com.my.example.atm.service.dao.repository;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.repository.AccountRepository;
import com.my.example.atm.service.TestingProperties;
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
public class AccountRepoTest extends TestingProperties {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.saveAll(bulkAccounts);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    // TODO: Unit Test Case Point 2
    @Test
    void whenFindByAccountNumberAndPinIsSuccess(){
        Account result = accountRepository.findByAccountNumberAndPin(account1.getAccountNumber(), account1.getPin());
        Assertions.assertEquals(account1, result);
    }

    @Test
    void whenFindByAccountNumber(){
        Account result = accountRepository.findByAccountNumber(account1.getAccountNumber());
        Assertions.assertEquals(account1, result);
    }
}
