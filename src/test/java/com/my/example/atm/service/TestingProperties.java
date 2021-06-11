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

/**
 * todo : Please fullfill these expected unit tests
 * The Expected
 * When adding an account, this account is expected to be there when getting user by number account - ???
 * When adding an account, this account is expected to be there when getting list of accounts - ???
 * When creating a withdraw transaction to an account, this account is expected to have its balance reduced by the amount indicated in the withdraw - Done*
 * When creating a withdraw transaction to an account, and the account does not have enough funds, it's expected to receive an error - Done*
 * When creating a transfer transaction from account1 to account2, it's expected to have account1's balance
 * reduced by the amount of the transaction and have account2's balance increased by the amount of the transaction- Done*
 * When creating a transfer transaction from account1 to account2, and account1 does not have enough funds, operation should throw an error- Done*
 * When creating a transfer transaction from account1 to account2, and account2 does not exist, then operation should fail- Done*
 * When creating a succesful transaction, it's expected to have this transaction when getting list of last 10 transactions - Done
 * When creating a bunch of succesful transactions, it's expected to have this list of transactions when getting list of last 10 transactions - Done
 * When creating more than 10 transactions, it's expected to have the 10 most recent transactions when getting list of last 10 transactions - Done
 *
 * */
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
    public final Account accountNotEnoughBalance = new Account("112233", "012108", "John Doe", 0L);

    public final Long withdrawAmount = 10L;
    public final Long transferAmount = 5L;
    public final Transaction.Withdraw withdraw = new Transaction.Withdraw(account1.getAccountNumber(),
            LocalDateTime.now(),
            withdrawAmount);
    public final String refNumber = "XXX";

    public final List<Transaction> fiveTransactions = Arrays.asList(
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now().minusDays(2), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"));

    public final List<Transaction> latestTenTransactions = Arrays.asList(
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Transfer(account1.getAccountNumber(), LocalDateTime.now(), account2.getAccountNumber(),
                    account1.getAccountNumber(), transferAmount, "XXX"),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount),
            new Transaction.Withdraw(account1.getAccountNumber(),
                    LocalDateTime.now(),
                    withdrawAmount),
            withdraw);

    public final List<Transaction> bulkTransactions = Stream.concat(latestTenTransactions.stream(), fiveTransactions.stream())
            .collect(Collectors.toList());

}
