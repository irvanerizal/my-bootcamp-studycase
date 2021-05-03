package service;

import entity.Account;

import java.util.Scanner;

public class TransferService {

    private static final Long MAX_AMOUNT_TRANSFER_LIMIT = 1000L;
    private static final Long MIN_AMOUNT_TRANSFER_LIMIT = 1L;

    private final AccountService accountService = new AccountService();

    public Integer transfer(Account userAccount, String destinationAccount, String transferAmount, Integer refrenceNumber){

        accountService.deductUserBalance(userAccount, Long.valueOf(transferAmount));
        accountService.addUserBalance(accountService.findAccount(destinationAccount), Long.valueOf(transferAmount));

        return showFundSummaryMenu(userAccount, destinationAccount, Long.valueOf(transferAmount), refrenceNumber.toString());
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

        return input.isEmpty() ? Utilities.SUMMARY_INPUT_TO_EXIT_APP :
                !Utilities.isNumber(input) ? Utilities.SUMMARY_INPUT_TO_EXIT_APP : Integer.parseInt(input);
    }

    public boolean validateUserAccount(String destinationAccountNo){
        if(!checkUserAccount(destinationAccountNo)){
            System.out.println("Invalid Account");
            return false;
        }
        return true;
    }

    public boolean validateTransferAmount(Account userAccount, String transferAmount){
        if(!Utilities.isNumber(transferAmount)){
            System.out.println("Amount input should only contains number");
            return false;
        } else if(Long.parseLong(transferAmount) > MAX_AMOUNT_TRANSFER_LIMIT){
            System.out.println("Maximum amount to withdraw is $1000");
            return false;
        } else if(Long.parseLong(transferAmount) < MIN_AMOUNT_TRANSFER_LIMIT){
            System.out.println("Minimum amount to withdraw is $1");
            return false;
        } else if(userAccount.getBalance() - Long.parseLong(transferAmount) < 0){
            System.out.println("Insufficient balance $" + transferAmount);
            return false;
        }
        return true;
    }

    public boolean checkUserAccount(String destinationAccountNo){
        return !destinationAccountNo.isEmpty() && Utilities.isNumber(destinationAccountNo) &&
                accountService.findAccount(destinationAccountNo) != null;
    }

    public boolean checkTransferAmount(Account userAccount, String transferAmount){
        return Utilities.isNumber(transferAmount) && (Long.parseLong(transferAmount) <= MAX_AMOUNT_TRANSFER_LIMIT)
                && (Long.parseLong(transferAmount) > MIN_AMOUNT_TRANSFER_LIMIT)
                && (userAccount.getBalance() - Long.parseLong(transferAmount) >= 0);
    }
}
