package service;

import entity.Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class DataLoaderService {

    private static final Integer DATA_LIMIT = 20;
    private static final String DELIMITER = ",";

    private final AccountService accountService = new AccountService();

    public boolean loadAccountData(String path) {
        boolean isLoadDataSuccess = false;
        try{
            String row;
            List<Account> accountList = new ArrayList<>();

            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            while ((row = csvReader.readLine()) != null){
                String[] acc = row.split(DELIMITER);
                Account account = new Account(acc[3], acc[1], acc[0], Long.parseLong(acc[2]));
                accountList.add(account);
            }

            validateAccountData(accountList);
            accountService.setAccount(new HashSet<>(accountList));
            isLoadDataSuccess = true;
        }
        catch (Exception e){
            System.out.println("Error while initialize data: " + e.getMessage());
        }
        return isLoadDataSuccess;
    }

    private void validateAccountData(List<Account> accountsSet) throws Exception {

        Set<Account> duplicateAccounts = new HashSet<>();
        Set<String> duplicateAccountNumbers = new HashSet<>();

        Optional<Account> isDataDuplicate = accountsSet.stream()
                .filter(account -> !duplicateAccounts.add(account))
                .findFirst();

        Optional<String> isAccountNumberDuplicate = accountsSet.stream()
                .map(Account::getAccountNumber)
                .filter(accountNo -> !duplicateAccountNumbers.add(accountNo))
                .findFirst();

        if(accountsSet.size() < DATA_LIMIT){
            throw new Exception("CSV file needs to have at least 20 records");
        } else if(isDataDuplicate.isPresent()){
            throw new Exception("There can't be duplicated records " + isDataDuplicate.get());
        } else if(isAccountNumberDuplicate.isPresent()){
            throw new Exception("There can't be 2 different accounts with the same Account Number " + isAccountNumberDuplicate.get());
        }
    }

}
