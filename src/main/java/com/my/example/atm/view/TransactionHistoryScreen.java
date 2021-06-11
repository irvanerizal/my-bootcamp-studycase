package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class has the responsibility to show the last transaction histories of the login user
 *
 * */
@Component
public class TransactionHistoryScreen {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    public void showTransactionHistoriesScreen(Account userAccount) {
        try {
            Account latestAccount = accountService.findAccount(userAccount.getAccountNumber());
            System.out.println("User Latest Balance : " + latestAccount.getBalance());
            System.out.println("*** User Transaction Histories ***");
            List<Transaction> userTransactions = transactionService.getTransactionByUserAccount(userAccount.getAccountNumber());
            userTransactions.forEach(transaction -> System.out.println(formattingTransactionHistories(transaction)));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String formattingTransactionHistories(Transaction transaction){
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction Type : ").append(transaction.getType()).append("\n");
        sb.append("Created By : ").append(transaction.getCreatedBy()).append("\n");
        sb.append("Date Time : ").append(transaction.getDateTime().format(Utilities.DATE_FORMATTER)).append("\n");

        if (transaction.getType().equals(Transaction.TransactionType.WITHDRAW)) {
            Transaction.Withdraw withdraw = (Transaction.Withdraw) transaction;
            sb.append("Amount : ").append(withdraw.getAmount()).append("\n");
        } else {
            Transaction.Transfer transfer = (Transaction.Transfer) transaction;
            sb.append("Origin Sender Acc : ").append(transfer.getOriginAccountNo()).append("\n");
            sb.append("Destination Receiver Acc : ").append(transfer.getDestinationAccountNo()).append("\n");
            sb.append("Refrence Number : ").append(transfer.getRefrenceNumber()).append("\n");
            sb.append("Transfer Amount : ").append(transfer.getAmount()).append("\n");
        }
        sb.append("--- *** ---");
        return sb.toString();
    }
}
