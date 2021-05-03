package service;

import entity.Account;

import java.time.LocalDateTime;
import java.util.Scanner;

public class WithdrawService {

    private final AccountService accountService = new AccountService();

    public Integer withdraw(Account userAccount, long withdrawnAmount) {
        if (!accountService.validateWithdrawTransaction(userAccount, withdrawnAmount)) {
            System.out.println("Insufficient balance $" + withdrawnAmount +"!\n");
            return Utilities.SUMMARY_INPUT_TO_RESET;
        }
        accountService.deductUserBalance(userAccount, withdrawnAmount);
        return showWithdrawSummaryMenu(userAccount, withdrawnAmount);
    }

    public Integer showWithdrawSummaryMenu(Account userAccount, Long withdrawnAmount) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Summary");
        System.out.println("Date : " + LocalDateTime.now().format(Utilities.DATE_FORMATTER));
        System.out.println("Withdraw : " + withdrawnAmount);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");
        String input = scanner.nextLine();

        return input.isEmpty() ? Utilities.SUMMARY_INPUT_TO_BACK_TRANSACTION_MENU :
                !Utilities.isNumber(input) ? Utilities.SUMMARY_INPUT_TO_BACK_TRANSACTION_MENU : Integer.parseInt(input);
    }
}
