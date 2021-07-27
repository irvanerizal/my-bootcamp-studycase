package com.my.example.atm.dao.repository;

import com.my.example.atm.dao.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /*@Query(value = "SELECT t FROM Transaction t WHERE t.createdBy = :accountNumber " +
            "ORDER BY t.dateTime DESC LIMIT :limit")
    List<Transaction> findTransactionByAccountNoDesc3(@Param("accountNumber") String accountNumber,
                                                      @Param("limit") Integer limit);*/

    List<Transaction> findTransactionByCreatedBy(String accountNumber, Pageable pageable);
    Page<Transaction> findByCreatedBy(String accountNumber, Pageable pageable);
}
