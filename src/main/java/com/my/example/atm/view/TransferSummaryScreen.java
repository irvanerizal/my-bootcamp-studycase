package com.my.example.atm.view;

import com.my.example.atm.entity.Account;
import com.my.example.atm.service.TransferService;
import com.my.example.atm.service.Utilities;

import java.util.Scanner;

/**
 * This class has responsibility to show transfer summary menu screen
 * This will call the transfer action
 * */
public class TransferSummaryScreen {

    private final TransferService transferService = new TransferService();

    public Integer showFundSummaryScreen(Account userAccount, String destinationAccountNo,
                                         String transferAmount, String refrenceNumber) {
        try {
            transferService.transfer(userAccount, destinationAccountNo, transferAmount, refrenceNumber);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Fund Transfer Summary");
            System.out.println("Destination com.my.example.atm.entity.Account : " + destinationAccountNo);
            System.out.println("Transfer Amount : " + transferAmount);
            System.out.println("Reference Number : " + refrenceNumber);
            System.out.println("Balance : " + userAccount.getBalance());

            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.println("Choose option[2]");

            String input = scanner.nextLine();
            return input.isEmpty() ? Utilities.EXIT_APP :
                    !Utilities.isNumber(input) ? Utilities.EXIT_APP : Integer.parseInt(input);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Utilities.RESET;
        }
    }


}
