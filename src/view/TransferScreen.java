package view;

import entity.Account;
import service.TransferService;
import service.Utilities;

import java.util.Scanner;

/**
 * This class has responsibility to show transfer menu screen
 *
 * */
public class TransferScreen {

    private final TransferService transferService = new TransferService();
    private final TransferSummaryScreen transferSummaryScreen = new TransferSummaryScreen();

    public Integer launchFundTransferScreen(Account userAccount) {

        Integer transactionResult = Utilities.RESET;

        while (isValidOnTransferScreen(transactionResult)) {
            try {
                String destinationInput;
                String transferAmountInput;

                destinationInput = showTransferScreenStage1();
                if (destinationInput.isEmpty()) return transactionResult;
                if (!transferService.isAccountValid(destinationInput)) {
                    continue;
                }

                transferAmountInput = showTransferScreenStage2();
                if (transferAmountInput.isEmpty()) return transactionResult;
                // guard clause
                // if (negative condition) then exit
                // regular execution
                // TODO: Not valid to put check amount here (relate user balance) - based Javier opinion
               /* if (!transferService.isTransferAmountValid(userAccount, transferAmountInput)) {
                    continue;
                }*/
                transactionResult = launchTransferConfirmation(userAccount, destinationInput, transferAmountInput, transactionResult);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return transactionResult;
    }

    private Integer launchTransferConfirmation(Account userAccount, String destinationInput,
                                               String transferAmountInput, Integer transactionResult){

        Integer refNumber = Utilities.generateRefNumber();
        showTransferScreenStage3(refNumber);

        String transferConfirmInput = showTransferScreenStage4(destinationInput, transferAmountInput, refNumber);
        switch (transferConfirmInput) {
            case "1":
                transactionResult = transferSummaryScreen.showFundSummaryScreen(userAccount, destinationInput,
                        transferAmountInput, refNumber.toString());
                break;
            case "2":
            case "":
                break;
        }
        return transactionResult;
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

    private String showTransferScreenStage4(String destinationAccInput, String transferAmountInput, Integer refNumber) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Transfer Confirmation");
        System.out.println("Destination entity.Account : " + destinationAccInput);
        System.out.println("Transfer Amount : " + transferAmountInput);
        System.out.println("Reference Number : " + refNumber);

        System.out.println("1. Confirm Trx");
        System.out.println("2. Cancel Trx");
        System.out.println("Choose option[2]");

        return scanner.nextLine();
    }
}
