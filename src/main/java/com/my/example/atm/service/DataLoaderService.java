package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
* This class has responsible to initialize account data.
 * Either it will load from a CSV file or the existing static data
*
* */
@Service
public class DataLoaderService {

    private static final Integer DATA_LIMIT = 20;
    private static final String DELIMITER = ",";

    @Autowired
    private AccountService accountService;

    public boolean loadAccountData(String path) {

        boolean isLoadDataSuccess = false;
        try {
            List<Account> accounts;
            if(path.isEmpty()){
                accountService.setAccount(new HashSet<>(accountService.getStaticAccounts()));
                return true;
            }

            accounts = Files.readAllLines(Paths.get(path)).stream()
                    .map(row -> {
                        String[] acc = row.split(DELIMITER);
                        Account account = new Account(acc[3], acc[1], acc[0], Long.parseLong(acc[2]));
                        return account;
                    }).collect(Collectors.toList());

            List<Account> validatedAccounts = validateAccountData(accounts);
            accountService.setAccount(new HashSet<>(validatedAccounts));
            isLoadDataSuccess = true;

        } catch (Exception e) {
            System.out.println("Error while initialize data: " + e.getMessage());
        }
        return isLoadDataSuccess;
    }

    private List<Account> validateAccountData(List<Account> accounts) throws Exception {
        Set<Account> nonDuplicateAccounts = new HashSet<>();

        Optional<Account> isDataDuplicate = accounts.stream()
                .filter(account -> !nonDuplicateAccounts.add(account))
                .findFirst();

        if (accounts.size() < DATA_LIMIT) {
            throw new Exception("CSV file needs to have at least 20 records");
        } else if (isDataDuplicate.isPresent()) {
            System.out.println("There can't be duplicated records " + isDataDuplicate.get());
        }

        return new ArrayList<>(nonDuplicateAccounts);
    }
}
