package service;

import entity.Account;

import java.util.Set;

public class AccountService {

    //Act as stores/database
    private static Set<Account> accountData;

    public void setAccount(Set<Account> accounts){
        accountData = accounts;
    }

    public void deductUserBalance(Account userAccount, Long withdrawAmount){
        accountData.remove(userAccount);
        Long newBalance = userAccount.getBalance() - withdrawAmount;
        userAccount.setBalance(newBalance);
        accountData.add(userAccount);
    }

    public void addUserBalance(Account userAccount, Long transferAmount){
        accountData.remove(userAccount);
        Long newBalance = userAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
        accountData.add(userAccount);
    }

    public Account validateAccount(String accountNo, String pin){
        return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNo)
                        && account.getPin().equals(pin))
                .findAny().orElse(null);
    }

    public Account findAccount(String accountNumber){
        return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findAny().orElse(null);
    }

    public boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }

}
