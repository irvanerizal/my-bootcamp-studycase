package service;

import entity.Account;
import entity.Transaction;

import java.time.LocalDateTime;

/**
 * This class has several methods and property relate with the withdraw transaction business logic
 *
 * */
public class WithdrawService {

    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();


    public void withdraw(Account userAccount, long withdrawnAmount) throws Exception {
        if (!accountService.validateWithdrawTransaction(userAccount, withdrawnAmount)) {
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
}
