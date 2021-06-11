package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.exception.InsufficientBalanceException;
import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import com.my.example.atm.service.api.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * This class has several methods and property relate with the transfer transaction business logic
 */
@Service
public class TransferServiceImpl implements TransferService {

    private static final Long MAX_AMOUNT_TRANSFER_LIMIT = 1000L;
    private static final Long MIN_AMOUNT_TRANSFER_LIMIT = 1L;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public void transfer(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) throws Exception {
        if (isAccountValid(destinationAccount)
                && isTransferAmountValid(userAccount, transferAmount)) {
            doTransfer(userAccount, destinationAccount, transferAmount, refrenceNumber);
        }
    }

    private void doTransfer(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) throws Exception {
        Account targetAccount = accountService.findAccount(destinationAccount);

        accountService.deductUserBalance(userAccount, Long.valueOf(transferAmount));
        accountService.addUserBalance(targetAccount, Long.valueOf(transferAmount));
        logTransaction(userAccount, destinationAccount, transferAmount, refrenceNumber);

    }

    @Override
    public boolean isAccountValid(String destinationAccountNo) throws Exception {
        if (!destinationAccountNo.isEmpty() && !Utilities.isNumber(destinationAccountNo) &&
                null == accountService.findAccount(destinationAccountNo))
            throw new UserNotFoundException("Invalid Account!\n");
        return true;
    }

    public boolean isTransferAmountValid(Account userAccount, String transferAmount) throws Exception {
        if (!Utilities.isNumber(transferAmount)) throw new Exception("Amount input should only contains number!\n");
        else if (Long.parseLong(transferAmount) > MAX_AMOUNT_TRANSFER_LIMIT)
            throw new Exception("Maximum amount to withdraw is $1000!\n");
        else if (Long.parseLong(transferAmount) < MIN_AMOUNT_TRANSFER_LIMIT)
            throw new Exception("Minimum amount to withdraw is $1!\n");
        else if (userAccount.getBalance() - Long.parseLong(transferAmount) < 0)
            throw new InsufficientBalanceException("Insufficient balance $" + transferAmount + "!\n");
        return true;
    }

    private void logTransaction(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) {

        Transaction.Transfer sender = new Transaction.Transfer(userAccount.getAccountNumber(),
                LocalDateTime.now(),
                destinationAccount,
                userAccount.getAccountNumber(),
                Long.parseLong(transferAmount),
                refrenceNumber);

        Transaction.Transfer receiver = new Transaction.Transfer(destinationAccount,
                LocalDateTime.now(),
                destinationAccount,
                userAccount.getAccountNumber(),
                Long.parseLong(transferAmount),
                refrenceNumber);

        transactionService.saveTransation(sender);
        transactionService.saveTransation(receiver);
    }

}