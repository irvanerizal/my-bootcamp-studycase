package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.TransferService;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show transfer menu screen
 *
 * */
@Component
public class TransferScreen {

    private static final String CONFIRM_MENU = "1";
    private static final String CANCEL_MENU = "2";

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferSummaryScreen transferSummaryScreen;

    public Integer launchFundTransferScreen(Account userAccount) {

        Integer transactionResult = Utilities.RESET;

        while (isValidOnTransferScreen(transactionResult)) {
            try {
                String destinationInput = showTransferScreenStage1();
                if (destinationInput.isEmpty()) return transactionResult;
                if (!transferService.isAccountValid(destinationInput)) {
                    continue;
                }

                String transferAmountInput = showTransferScreenStage2();
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
            case CONFIRM_MENU:
                transactionResult = transferSummaryScreen.showFundSummaryScreen(userAccount, destinationInput,
                        transferAmountInput, refNumber.toString());
                break;
            case CANCEL_MENU:
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
        System.out.println("Destination com.my.example.atm.dao.entity.Account : " + destinationAccInput);
        System.out.println("Transfer Amount : " + transferAmountInput);
        System.out.println("Reference Number : " + refNumber);

        System.out.println("1. Confirm Trx");
        System.out.println("2. Cancel Trx");
        System.out.println("Choose option[2]");

        return scanner.nextLine();
    }
}
