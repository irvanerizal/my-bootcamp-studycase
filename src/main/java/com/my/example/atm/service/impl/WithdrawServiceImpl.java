package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import com.my.example.atm.service.api.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * This class has several methods and property relate with the withdraw transaction business logic
 *
 * */
@Component
public class WithdrawServiceImpl implements WithdrawService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @Override
    public void withdraw(Account userAccount, long withdrawnAmount) throws Exception {
        if (!validateWithdrawTransaction(userAccount, withdrawnAmount)) {
            throw new InsufficientBalanceException("Insufficient balance $" + withdrawnAmount +"!\n");
        }
        accountService.deductUserBalance(userAccount, withdrawnAmount);
//        throw new RuntimeException("Boom! This happens to simulate transactional rollback"); //to simulate transactional rollback
        logTransaction(userAccount, withdrawnAmount);
    }

    private void logTransaction(Account userAccount, long withdrawnAmount){

        Transaction.Withdraw withdraw = new Transaction.Withdraw(userAccount.getAccountNumber(),
                LocalDateTime.now(),
                withdrawnAmount);
        transactionService.saveTransation(withdraw);
    }

    @Override
    public boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }
}
