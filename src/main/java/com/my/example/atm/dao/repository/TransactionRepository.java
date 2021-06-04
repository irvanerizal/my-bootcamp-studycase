package com.my.example.atm.dao.repository;

import com.my.example.atm.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCreatedBy(String accountNumber);
}
