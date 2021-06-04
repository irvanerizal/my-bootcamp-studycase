package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * This class has several methods and property relate with the withdraw transaction business logic
 *
 * */
@Component
public class WithdrawService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    public void withdraw(Account userAccount, long withdrawnAmount) throws Exception {
        if (!validateWithdrawTransaction(userAccount, withdrawnAmount)) {
            throw new Exception("Insufficient balance $" + withdrawnAmount +"!\n");
        }
        accountService.deductUserBalance(userAccount, withdrawnAmount);
        logTransaction(userAccount, withdrawnAmount);

    }

    private void logTransaction(Account userAccount, long withdrawnAmount){

        Transaction.Withdraw withdraw = new Transaction.Withdraw(userAccount.getAccountNumber(),
                LocalDateTime.now(),
                withdrawnAmount);
        transactionService.saveTransation(withdraw);
    }

    public boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }
}
