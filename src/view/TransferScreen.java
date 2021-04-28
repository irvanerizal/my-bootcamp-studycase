package view;

import entity.Account;
import service.AccountService;
import service.Utilities;

import java.util.Scanner;

public class TransferScreen {

    private AccountService accountService = new AccountService();

    private Utilities atmUtils = new Utilities();

    public Integer launchFundTransferScreen(Account userAccount) {

        String destinationAccInput = "init";
        String transferAmountInput = "init";
        String summaryInput = "0";

        boolean isDestinationAccValid = false;
        boolean isTransferAmountValid = false;
        int refNumber;

        while (!destinationAccInput.isEmpty() && !transferAmountInput.isEmpty()
                && summaryInput.equals("0")){

            String confirmInput = "init";

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter destination account and press enter to continue or \n" +
                    "press enter to go back to Transaction:");
            destinationAccInput = scanner.nextLine();

            if(!destinationAccInput.isEmpty()){
                isDestinationAccValid = validateUserAccount(destinationAccInput);
            }
            scanner.reset();

            while (isDestinationAccValid && !transferAmountInput.isEmpty()
                    && (!confirmInput.isEmpty() && !confirmInput.equals("2"))
                    && summaryInput.equals("0")){

                System.out.println("Please enter transfer amount and \n" +
                        "press enter to continue or \n" +
                        "press enter to go back to Transaction:");
                transferAmountInput = scanner.nextLine();

                if(!transferAmountInput.isEmpty()){
                    isTransferAmountValid = validateTransferAmount(userAccount, transferAmountInput);
                }
                scanner.reset();

                while (isTransferAmountValid
                        && (!confirmInput.isEmpty() && !confirmInput.equals("2"))
                        && summaryInput.equals("0")){

                    refNumber = atmUtils.generateRefNumber();
                    System.out.println("Reference Number: " + refNumber + "\n" +
                            "press enter to continue");
                    scanner.nextLine(); scanner.reset();

                    System.out.println("Transfer Confirmation");
                    System.out.println("Destination entity.Account : " + destinationAccInput);
                    System.out.println("Transfer Amount : " + transferAmountInput.toString());
                    System.out.println("Reference Number : " + refNumber);

                    System.out.println("1. Confirm Trx");
                    System.out.println("2. Cancel Trx");
                    System.out.println("Choose option[2]");

                    confirmInput = scanner.nextLine();
                    switch (confirmInput){
                        case "1":
                            accountService.deductUserBalance(userAccount, Long.valueOf(transferAmountInput));
                            accountService.addUserBalance(accountService.findAccount(destinationAccInput), Long.valueOf(transferAmountInput));

                            summaryInput = showFundSummaryMenu(userAccount, destinationAccInput, Long.valueOf(transferAmountInput), Integer.toString(refNumber));
                            break;
                        case "2":
                        case "":
                            destinationAccInput = "init";
                            transferAmountInput = "init";
                            isDestinationAccValid = false;
                            isTransferAmountValid = false;
                            break;
                    }
                }
            }
        }
        return summaryInput.isEmpty()? 2 : Integer.parseInt(summaryInput);
    }

    private boolean validateUserAccount(String destinationAccountNo){
        if(destinationAccountNo.isEmpty() || !atmUtils.isNumber(destinationAccountNo) ||
                accountService.findAccount(destinationAccountNo) == null){
            System.out.println("Invalid entity.Account");
            return false;
        }
        return true;
    }

    private boolean validateTransferAmount(Account userAccount, String transferAmount){
        if(!atmUtils.isNumber(transferAmount)){
            System.out.println("Amount input should only contains number");
            return false;
        } else if(Long.parseLong(transferAmount) > 1000l){
            System.out.println("Maximum amount to withdraw is $1000");
            return false;
        } else if(Long.parseLong(transferAmount) < 1l){
            System.out.println("Minimum amount to withdraw is $1");
            return false;
        } else if(userAccount.getBalance() - Long.parseLong(transferAmount) < 0){
            System.out.println("Insufficient balance $" + transferAmount);
            return false;
        }
        return true;
    }

    private static String showFundSummaryMenu(Account userAccount, String destinationAccountNo,
                                              Long transferAmount, String refrenceNumber) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fund Transfer Summary");
        System.out.println("Destination entity.Account : " + destinationAccountNo);
        System.out.println("Transfer Amount : " + transferAmount.toString());
        System.out.println("Reference Number : " + refrenceNumber);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");
        return scanner.nextLine();
    }
}
