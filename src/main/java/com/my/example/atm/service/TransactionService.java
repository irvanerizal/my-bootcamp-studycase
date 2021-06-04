package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.dao.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class has several methods and property relate with the transaction (history) business logic
 */
@Service
public class TransactionService {

    private static final Integer QUERY_LIMIT = 10;

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransation(Transaction transaction) {

        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByAccountNumberSorted(String accountNumber) {

        return transactionRepository.findByCreatedBy(accountNumber)
                .stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
        /*return transactionData.stream()
                .filter(transaction -> transaction.getCreatedBy().equals(accountNumber))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());*/
    }

    public List<Transaction> getTransactionByUserAccount(String accountNumber) {
        //Sort by date (latest) and implement the limit in the com.my.example.atm.service
        return getTransactionsByAccountNumberSorted(accountNumber).stream()
                .limit(QUERY_LIMIT)
                .collect(Collectors.toList());
    }
}
