package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class has several methods and property relate with the account business logic
 *
 * */
@Service
public class AccountService {

    private static final List<Account> STATIC_ACCOUNTS = Arrays.asList(
            new Account("112233", "012108", "John Doe", 100L),
            new Account("112244", "932012", "Jane Doe", 30L)
    );
    //Act as stores/database
//    private static Set<Account> accountData;

    @Autowired
    private AccountRepository accountRepository;

    public void setAccount(Set<Account> accounts){
//        accountData = accounts;
        accountRepository.saveAll(accounts);
    }

    public List<Account> getStaticAccounts(){
        return STATIC_ACCOUNTS;
    }

    public Account validateAccount(String accountNo, String pin) throws Exception {

        return Optional.of(accountRepository.findByAccountNumberAndPin(accountNo, pin))
                .orElseThrow(() -> new UserNotFoundException("Account not found"));

        /*return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNo)
                        && account.getPin().equals(pin))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("Account not found"));*/
    }

    public Account findAccount(String accountNumber) throws Exception{

        return Optional.of(accountRepository.findByAccountNumber(accountNumber))
                .orElseThrow(() -> new UserNotFoundException("Account not found"));

        /*return accountData.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("Account not found"));*/
    }

    public void deductUserBalance(Account userAccount, Long withdrawAmount){

        Account targetAccount = accountRepository.findByAccountNumber(userAccount.getAccountNumber());
        Long newBalance = targetAccount.getBalance() - withdrawAmount;
        targetAccount.setBalance(newBalance);
        accountRepository.save(targetAccount);

        /*accountData.remove(userAccount);
        Long newBalance = userAccount.getBalance() - withdrawAmount;
        userAccount.setBalance(newBalance);
        accountData.add(userAccount);*/
    }

    public void addUserBalance(Account userAccount, Long transferAmount){

        Account targetAccount = accountRepository.findByAccountNumber(userAccount.getAccountNumber());
        Long newBalance = targetAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
        accountRepository.save(targetAccount);

        /*accountData.remove(userAccount);
        Long newBalance = userAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
        accountData.add(userAccount);*/
    }



}
