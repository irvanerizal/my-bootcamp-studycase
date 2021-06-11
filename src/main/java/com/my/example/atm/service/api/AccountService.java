package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {

    void setAccount(Set<Account> accounts);

    List<Account> getStaticAccounts();

    Account validateAccount(String accountNo, String pin) throws Exception;

    Account findAccount(String accountNumber) throws Exception;

    void deductUserBalance(Account userAccount, Long withdrawAmount);

    void addUserBalance(Account userAccount, Long transferAmount);
}
