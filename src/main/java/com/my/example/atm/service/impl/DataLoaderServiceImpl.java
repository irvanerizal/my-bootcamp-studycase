package com.my.example.atm.service.impl;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.exception.DataNotValidException;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.DataLoaderService;
import com.my.example.atm.service.api.FileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* This class has responsible to initialize account data.
 * Either it will load from a CSV file or the existing static data
*
* */
@Service
public class DataLoaderServiceImpl implements DataLoaderService {

    private static final Integer DATA_LIMIT = 20;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileReaderService fileReaderService;

    @Override
    public void loadAccountData(String path) throws Exception {
            if(path.isEmpty()){
                accountService.setAccount(new HashSet<>(accountService.getStaticAccounts()));
                return ;
            }
            List<Account> validatedAccounts = validateAccountData(fileReaderService.readAccount(path));
            accountService.setAccount(new HashSet<>(validatedAccounts));
    }

    private List<Account> validateAccountData(List<Account> accounts) throws Exception {
        Set<Account> nonDuplicateAccounts = new HashSet<>();

        Optional<Account> isDataDuplicate = accounts.stream()
                .filter(account -> !nonDuplicateAccounts.add(account))
                .findFirst();

        if (accounts.size() < DATA_LIMIT) {
            throw new DataNotValidException("CSV file needs to have at least 20 records");
        } else if (isDataDuplicate.isPresent()) {
            System.out.println("There can't be duplicated records " + isDataDuplicate.get());
        }
        return new ArrayList<>(nonDuplicateAccounts);
    }
}
