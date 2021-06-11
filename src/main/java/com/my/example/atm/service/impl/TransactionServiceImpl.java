package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.dao.repository.TransactionRepository;
import com.my.example.atm.service.api.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class has several methods and property relate with the transaction (history) business logic
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Integer QUERY_LIMIT = 10;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransation(Transaction transaction) {

        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionByUserAccount(String accountNumber) {
        return transactionRepository.findTransactionByAccountNoDesc(accountNumber)
                .stream()
                .limit(QUERY_LIMIT)
                .collect(Collectors.toList());
    }
}
