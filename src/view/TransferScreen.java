package view;

import entity.Account;
import service.Utilities;

import java.util.Scanner;


public class TransferScreen {

    public final TransferSummaryScreen transferSummaryScreen = new TransferSummaryScreen();

    public Integer launchFundTransferScreen(Account userAccount) {

        Integer summaryInput = Utilities.RESET;

        while (isValidOnTransferScreen(summaryInput)) {
            try {
                String destinationInput;
                String transferAmountInput;

                destinationInput = showTransferScreenStage1();
                if (destinationInput.isEmpty()) return summaryInput;
                if (!transferSummaryScreen.isUserAccountValid(destinationInput)) {
                    continue;
                }

                transferAmountInput = showTransferScreenStage2();
                if (transferAmountInput.isEmpty()) return summaryInput;
                // guard clause
                // if (negative condition) then exit
                // regular execution
                if (!transferSummaryScreen.isTransferAmountValid(userAccount, transferAmountInput)) {
                    continue;
                }

                summaryInput = launchTransferConfirmation(userAccount, destinationInput, transferAmountInput, summaryInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return summaryInput;
    }

    private Integer launchTransferConfirmation(Account userAccount, String destinationInput,
                                               String transferAmountInput, Integer summaryInput){

        Integer refNumber = Utilities.generateRefNumber();
        showTransferScreenStage3(refNumber);

        String transferConfirmInput = showTransferScreenStage4(destinationInput, Long.parseLong(transferAmountInput), refNumber);
        switch (transferConfirmInput) {
            case "1":
                summaryInput = transferSummaryScreen.showFundSummaryScreen(userAccount, destinationInput, Long.valueOf(transferAmountInput), refNumber.toString());
                break;
            case "2":
            case "":
                break;
        }
        return summaryInput;
    }

    private boolean isValidOnTransferScreen(Integer summaryInput) {
        return summaryInput.equals(Utilities.RESET);
    }

    private String showTransferScreenStage1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter destination account and press enter to continue or \n" +
                "press enter to go back to Transaction:");
        return scanner.nextLine();
    }

    private String showTransferScreenStage2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter transfer amount and \n" +
                "press enter to continue or \n" +
                "press enter to go back to Transaction:");
        return scanner.nextLine();
    }

    private String showTransferScreenStage3(Integer refNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reference Number: " + refNumber + "\n" +
                "press enter to continue");
        return scanner.nextLine();
    }

    private String showTransferScreenStage4(String destinationAccInput, Long transferAmountInput, Integer refNumber) {
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
}
