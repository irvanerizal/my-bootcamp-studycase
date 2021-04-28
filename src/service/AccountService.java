package service;

import entity.Account;


import java.util.Arrays;
import java.util.List;

public class AccountService {

    private static final List<Account> ACCOUNTS = Arrays.asList(
            new Account("112233", "012108", "John Doe", 100l),
            new Account("112244", "932012", "Jane Doe", 30l)
    );

    public void deductUserBalance(Account userAccount, Long withdrawAmount){
        Long newBalance = userAccount.getBalance() - withdrawAmount;
        userAccount.setBalance(newBalance);
    }

    public void addUserBalance(Account userAccount, Long transferAmount){
        Long newBalance = userAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
    }

    public Account validateAccount(String accountNo, String pin){
        return ACCOUNTS.stream()
                .filter(account -> account.getAccountNumber().equals(accountNo)
                        && account.getPin().equals(pin))
                .findAny().orElse(null);
    }

    public Account findAccount(String accountNumber){
        return ACCOUNTS.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findAny().orElse(null);
    }

    public boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }

}
