package com.my.example.atm.dao.repository;

import com.my.example.atm.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumberAndPin(String accountNumber, String pin);
    Account findByAccountNumber(String accountNumber);
}
