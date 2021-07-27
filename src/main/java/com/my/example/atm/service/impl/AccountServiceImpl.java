package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.repository.AccountRepository;
import com.my.example.atm.exception.UserNotFoundException;
import com.my.example.atm.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class AccountServiceImpl implements AccountService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final List<Account> STATIC_ACCOUNTS = Arrays.asList(
            new Account("112233", encoder.encode("012108"), "John Doe", 100L),
            new Account("112244", encoder.encode("932012"), "Jane Doe", 30L)
    );

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void setAccount(Set<Account> accounts){
        accountRepository.saveAll(accounts);
    }

    @Override
    public List<Account> getStaticAccounts(){
        return STATIC_ACCOUNTS;
    }

    @Override
    public Account validateAccount(String accountNo, String pin) throws Exception {
        return Optional.ofNullable(accountRepository.findByAccountNumberAndPin(accountNo, pin))
                .orElseThrow(() -> new UserNotFoundException("Account not found"));
    }

    @Override
    public Account findAccount(String accountNumber) throws Exception{
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber))
                .orElseThrow(() -> new UserNotFoundException("Account not found"));

    }

    @Override
    public void deductUserBalance(Account userAccount, Long withdrawAmount){

        Account targetAccount = accountRepository.findByAccountNumber(userAccount.getAccountNumber());
        Long newBalance = targetAccount.getBalance() - withdrawAmount;
        targetAccount.setBalance(newBalance);
        accountRepository.save(targetAccount);

    }

    @Override
    public void addUserBalance(Account userAccount, Long transferAmount){

        Account targetAccount = accountRepository.findByAccountNumber(userAccount.getAccountNumber());
        Long newBalance = targetAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
        accountRepository.save(targetAccount);

    }
}
