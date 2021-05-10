package view;

import entity.Account;
import service.AccountService;
import service.Utilities;

import java.time.LocalDateTime;
import java.util.Scanner;

public class WithdrawSummaryScreen {

    private final AccountService accountService = new AccountService();

    public Integer showWithdrawSummaryScreen(Account userAccount, Long withdrawnAmount)  {

        try {
            withdraw(userAccount, withdrawnAmount);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Summary");
            System.out.println("Date : " + LocalDateTime.now().format(Utilities.DATE_FORMATTER));
            System.out.println("Withdraw : " + withdrawnAmount);
            System.out.println("Balance : " + userAccount.getBalance());

            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.println("Choose option[2]");
            String input = scanner.nextLine();

            return input.isEmpty() ? Utilities.BACK_TRANSACTION_MENU :
                    !Utilities.isNumber(input) ? Utilities.BACK_TRANSACTION_MENU : Integer.parseInt(input);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Utilities.RESET;
        }
    }

    //Will be extracted as withdraw service
    private void withdraw(Account userAccount, long withdrawnAmount) throws Exception {
        if (!accountService.validateWithdrawTransaction(userAccount, withdrawnAmount)) {
            throw new Exception("Insufficient balance $" + withdrawnAmount +"!\n");
        }
        accountService.deductUserBalance(userAccount, withdrawnAmount);
    }
}
