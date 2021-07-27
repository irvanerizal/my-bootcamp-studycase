package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TransactionService {
    void saveTransation(Transaction transaction);

    List<Transaction> getTransactionByUserAccount(String accountNumber);

    Page<Transaction> getTransactionPageByUserAccount(String accountNumber, PageRequest pageRequest);
}
