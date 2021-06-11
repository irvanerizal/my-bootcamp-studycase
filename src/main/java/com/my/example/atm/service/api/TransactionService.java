package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransation(Transaction transaction);

    List<Transaction> getTransactionByUserAccount(String accountNumber);
}
