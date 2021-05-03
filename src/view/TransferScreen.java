package view;

import entity.Account;
import service.TransferService;
import service.Utilities;

import java.util.Scanner;

public class TransferScreen {

    public final TransferService transferService = new TransferService();

    private static final String RESET_VALUE = "";

    public Integer launchFundTransferScreen(Account userAccount) {

        String destinationInput = RESET_VALUE;
        String transferAmountInput = RESET_VALUE;
        String transferConfirmInput;
        Integer summaryInput = Utilities.SUMMARY_INPUT_TO_RESET;
        Integer refNumber;

        while (summaryInput.equals(Utilities.SUMMARY_INPUT_TO_RESET)){

            destinationInput = destinationInput.isEmpty() ? showTransferScreenStage1() :
                    !transferService.checkUserAccount(destinationInput)? showTransferScreenStage1() : destinationInput;

            if(destinationInput.isEmpty()) return summaryInput;
            if(transferService.validateUserAccount(destinationInput)) {

                transferAmountInput = transferAmountInput.isEmpty() ? showTransferScreenStage2() :
                        !transferService.checkTransferAmount(userAccount, transferAmountInput)? showTransferScreenStage2() : transferAmountInput;

                if(transferAmountInput.isEmpty()) return summaryInput;
                if(transferService.validateTransferAmount(userAccount, transferAmountInput)){

                    refNumber = Utilities.generateRefNumber();
                    showTransferScreenStage3(refNumber);

                    transferConfirmInput = showTransferScreenStage4(destinationInput, Long.parseLong(transferAmountInput), refNumber);
                    switch (transferConfirmInput){
                        case "1":
                            summaryInput = transferService.transfer(userAccount, destinationInput, transferAmountInput, refNumber);
                            break;
                        case "2":
                        case "":
                            destinationInput = transferAmountInput = RESET_VALUE;
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
}
