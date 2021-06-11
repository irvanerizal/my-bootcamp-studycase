package com.my.example.atm.dao.repository;

import com.my.example.atm.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.createdBy = :accountNumber " +
            "ORDER BY t.dateTime DESC")
    List<Transaction> findTransactionByAccountNoDesc(@Param("accountNumber") String accountNumber);
}
