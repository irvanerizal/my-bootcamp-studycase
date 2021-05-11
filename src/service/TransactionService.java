package service;

import entity.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    //Act as stores/database
    private static List<Transaction> transactionData = new ArrayList<>();

    public void setTransation(Transaction transaction) {
        transactionData.add(transaction);
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionData.stream()
                .filter(transaction -> transaction.getCreatedBy().equals(accountNumber))
                .collect(Collectors.toList());
    }

}
