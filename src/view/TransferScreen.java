package view;

import entity.Account;
import service.AccountService;
import service.TransferService;
import service.Utilities;

import java.util.Scanner;

public class TransferScreen {

    private AccountService accountService = new AccountService();
    private TransferService transferService = new TransferService();

    private static final String EXIT_TRANSFER_MENU = "";
    private static final String RESET_VALUE = "";

    /*public Integer launchFundTransferScreen(Account userAccount) {

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

                    refNumber = Utilities.generateRefNumber();
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
    }*/
    public Integer launchFundTransferScreen(Account userAccount) {

        String destinationAccInput = RESET_VALUE;
        String transferAmountInput = RESET_VALUE;
        String confirmInput = "";
        Integer summaryInput = Utilities.SUMMARY_RESET_MENU;
        Integer refNumber;

        boolean isDestinationAccValid = false;
        boolean isTransferAmountValid = false;

        while (summaryInput.equals(Utilities.SUMMARY_RESET_MENU)){

            destinationAccInput = destinationAccInput.isEmpty() ? showTransferScreenStage1() :
                    !checkUserAccount(destinationAccInput)? showTransferScreenStage1() : destinationAccInput;

            if(destinationAccInput.isEmpty()) return summaryInput;
            if(validateUserAccount(destinationAccInput)) {

                transferAmountInput = transferAmountInput.isEmpty() ? showTransferScreenStage2() :
                        !checkTransferAmount(userAccount, transferAmountInput)? showTransferScreenStage2() : transferAmountInput;

                if(transferAmountInput.isEmpty()) return summaryInput;
                if(validateTransferAmount(userAccount, transferAmountInput)){

                    refNumber = Utilities.generateRefNumber();
                    showTransferScreenStage3(refNumber);

                    confirmInput = showTransferScreenStage4(destinationAccInput, Long.parseLong(transferAmountInput), refNumber);
                    switch (confirmInput){
                        case "1":
                            summaryInput = transferService.transferUser(userAccount, destinationAccInput, transferAmountInput, refNumber);
                            break;
                        case "2":
                        case "":
                            destinationAccInput = transferAmountInput = RESET_VALUE;
                            break;
                    }
                }
            }
        }
        return summaryInput;
    }

    private String showTransferScreenStage1(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter destination account and press enter to continue or \n" +
                "press enter to go back to Transaction:");
        return scanner.nextLine();
    }

    private String showTransferScreenStage2(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter transfer amount and \n" +
                "press enter to continue or \n" +
                "press enter to go back to Transaction:");
        return scanner.nextLine();
    }

    private String showTransferScreenStage3(Integer refNumber){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reference Number: " + refNumber + "\n" +
                "press enter to continue");
        return scanner.nextLine();
    }

    private String showTransferScreenStage4(String destinationAccInput, Long transferAmountInput, Integer refNumber){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Transfer Confirmation");
        System.out.println("Destination entity.Account : " + destinationAccInput);
        System.out.println("Transfer Amount : " + transferAmountInput.toString());
        System.out.println("Reference Number : " + refNumber);

        System.out.println("1. Confirm Trx");
        System.out.println("2. Cancel Trx");
        System.out.println("Choose option[2]");

        return scanner.nextLine();
    }

    private boolean validateUserAccount(String destinationAccountNo){
        if(!checkUserAccount(destinationAccountNo)){
            System.out.println("Invalid Account");
            return false;
        }
        return true;
    }

    private boolean validateTransferAmount(Account userAccount, String transferAmount){
        if(!Utilities.isNumber(transferAmount)){
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

    private boolean checkUserAccount(String destinationAccountNo){
        return !destinationAccountNo.isEmpty() && Utilities.isNumber(destinationAccountNo) &&
                accountService.findAccount(destinationAccountNo) != null;
    }

    private boolean checkTransferAmount(Account userAccount, String transferAmount){
        return Utilities.isNumber(transferAmount) && (Long.parseLong(transferAmount) <= 1000L)
                && (Long.parseLong(transferAmount) > 1L) && (userAccount.getBalance() - Long.parseLong(transferAmount) >= 0);
    }
}
