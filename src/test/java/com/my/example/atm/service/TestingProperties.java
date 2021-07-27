package com.my.example.atm.service;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestingProperties {

    public final List<Account> accounts = Arrays.asList(
            new Account("112233", "012108", "John Doe", 100L),
            new Account("112244", "932012", "Jane Doe", 30L)
    );
    public final List<Account> bulkAccounts = Arrays.asList(
            new Account("112233", "012108", "John Doe", 100L),
            new Account("112244", "932012", "Jane Doe", 30L),
            new Account("111111", "012108", "John Doe", 100L),
            new Account("222222", "932012", "Jane Doe", 30L),
            new Account("333333", "012108", "John Doe", 100L),
            new Account("444444", "932012", "Jane Doe", 30L),
            new Account("555555", "012108", "John Doe", 100L),
            new Account("666666", "932012", "Jane Doe", 30L),
            new Account("777777", "012108", "John Doe", 100L),
            new Account("888888", "932012", "Jane Doe", 30L),
            new Account("999999", "012108", "John Doe", 100L),
            new Account("001010", "932012", "Jane Doe", 30L),
            new Account("001111", "012108", "John Doe", 100L),
            new Account("001212", "932012", "Jane Doe", 30L),
            new Account("001313", "012108", "John Doe", 100L),
            new Account("001414", "932012", "Jane Doe", 30L),
            new Account("001515", "012108", "John Doe", 100L),
            new Account("001616", "932012", "Jane Doe", 30L),
            new Account("001717", "012108", "John Doe", 100L),
            new Account("001818", "932012", "Jane Doe", 30L)
    );
    public final Set<Account> accountSet = new HashSet<>(accounts);
    public final Set<Account> bulkAccountSet = new HashSet<>(bulkAccounts);
    public final Account account1 = accounts.get(0);
    public final Account account2 = accounts.get(1);
    public final Account accountWithZeroBalance = new Account("112233", "012108", "John Doe", 0L);

    public final Integer querySize = 10;
    public final Long withdrawAmount10 = 10L;
    public final Long transferAmount5 = 5L;
    public final Transaction.Withdraw withdraw = new Transaction.Withdraw(account1.getAccountNumber(),
            LocalDateTime.now(),
            withdrawAmount10);
    public final String refNumber = "XXX";

    public final List<Transaction> fiveTransactions = Arrays.asList(
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"));

    public final List<Transaction> latestTenTransactions = Arrays.asList(
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount5, "XXX"),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount10),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount10),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount10),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount10),
            withdraw);

    public final List<Transaction> bulkTransactions = Stream.concat(latestTenTransactions.stream(), fiveTransactions.stream())
            .collect(Collectors.toList());

}
