package service;

import entity.Account;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * This class has several methods and property relate with the account business logic
 *
 * */
public class AccountService {

    private static final List<Account> ACCOUNTS = Arrays.asList(
            new Account("112233", "012108", "John Doe", 100L),
            new Account("112244", "932012", "Jane Doe", 30L)
    );
    //Act as stores/database
    private static Set<Account> accountData;

    public void setAccount(Set<Account> accounts){
        accountData = accounts;
    }

    public List<Account> getOldAccounts(){
        return ACCOUNTS;
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

    public Account validateAccount(String accountNo, String pin) throws Exception {
        return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNo)
                        && account.getPin().equals(pin))
                .findAny()
                .orElseThrow(() -> new Exception("Account not found"));
    }

    public Account findAccount(String accountNumber) throws Exception{
        return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findAny()
                .orElseThrow(() -> new Exception("Account not found"));
    }

    public boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }

}
