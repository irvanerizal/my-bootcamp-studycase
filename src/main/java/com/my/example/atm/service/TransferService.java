package com.my.example.atm.service;

import com.my.example.atm.entity.Account;
import com.my.example.atm.entity.Transaction;
import com.my.example.atm.exception.UserNotFoundException;

import java.time.LocalDateTime;

/**
 * This class has several methods and property relate with the transfer transaction business logic
 */
public class TransferService {


    private static final Long MAX_AMOUNT_TRANSFER_LIMIT = 1000L;
    private static final Long MIN_AMOUNT_TRANSFER_LIMIT = 1L;

    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();


    public void transfer(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) throws Exception {
        if (isAccountValid(destinationAccount)
                && isTransferAmountValid(userAccount, transferAmount)) {
            doTransfer(userAccount, destinationAccount, transferAmount, refrenceNumber);
        }
    }

    private void doTransfer(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) throws Exception {
        Account account = accountService.findAccount(destinationAccount);

        accountService.deductUserBalance(userAccount, Long.valueOf(transferAmount));
        accountService.addUserBalance(account, Long.valueOf(transferAmount));
        logTransaction(userAccount, destinationAccount, transferAmount, refrenceNumber);

    }

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
            throw new Exception("Insufficient balance $" + transferAmount + "!\n");
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