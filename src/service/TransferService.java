package service;

import entity.Account;

import java.util.Scanner;

public class TransferService {

    private final AccountService accountService = new AccountService();

    public Integer transferUser(Account userAccount, String destinationAccInput, String transferAmountInput, Integer refNumber){

        accountService.deductUserBalance(userAccount, Long.valueOf(transferAmountInput));
        accountService.addUserBalance(accountService.findAccount(destinationAccInput), Long.valueOf(transferAmountInput));

        return showFundSummaryMenu(userAccount, destinationAccInput, Long.valueOf(transferAmountInput), refNumber.toString());
    }

    private Integer showFundSummaryMenu(Account userAccount, String destinationAccountNo, Long transferAmount, String refrenceNumber) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fund Transfer Summary");
        System.out.println("Destination entity.Account : " + destinationAccountNo);
        System.out.println("Transfer Amount : " + transferAmount.toString());
        System.out.println("Reference Number : " + refrenceNumber);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");

        String input = scanner.nextLine();

        return input.isEmpty() ? Utilities.SUMMARY_EXIT_MENU :
                !Utilities.isNumber(input) ? Utilities.SUMMARY_EXIT_MENU : Integer.parseInt(input);
    }
}
