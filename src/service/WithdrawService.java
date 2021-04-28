package service;

import entity.Account;

import java.time.LocalDateTime;
import java.util.Scanner;

public class WithdrawService {

    private final AccountService accountService = new AccountService();

    public Integer withdraw(Account userAccount, long amount) {
        if (!accountService.validateWithdrawTransaction(userAccount, amount)) {
            System.out.println("Insufficient balance $" + amount);
            return Utilities.SUMMARY_RESET_MENU;
        }
        accountService.deductUserBalance(userAccount, amount);
        return showWithdrawSummaryMenu(userAccount, amount);
    }

    public Integer showWithdrawSummaryMenu(Account userAccount, Long deduction) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Summary");
        System.out.println("Date : " + LocalDateTime.now().format(Utilities.FORMATTER));
        System.out.println("Withdraw : " + deduction);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");
        String input = scanner.nextLine();

        return input.isEmpty() ? Utilities.SUMMARY_BACK_TRANSACTION_MENU : Integer.parseInt(input);
    }
}
