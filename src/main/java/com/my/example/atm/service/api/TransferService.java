package com.my.example.atm.service.api;

import com.my.example.atm.dao.entity.Account;

public interface TransferService {
    void transfer(Account userAccount, String destinationAccount, String transferAmount, String refrenceNumber) throws Exception;
    boolean isAccountValid(String destinationAccountNo) throws Exception;
    boolean isAmountValid(String transferAmount) throws Exception;
}
