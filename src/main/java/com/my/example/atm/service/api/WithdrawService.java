package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Account;

public interface WithdrawService {
    void withdraw(Account userAccount, long withdrawnAmount) throws Exception;

    boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount);
}
