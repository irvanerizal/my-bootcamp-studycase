package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.AccountService;
import com.my.example.atm.service.TransferService;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show transfer summary menu screen
 * This will call the transfer action
 */
@Component
public class TransferSummaryScreen {

    @Autowired
    private TransferService transferService;
    @Autowired
    private AccountService accountService;

    public Integer showFundSummaryScreen(Account userAccount, String destinationAccountNo,
                                         String transferAmount, String refrenceNumber) {
        try {
            transferService.transfer(userAccount, destinationAccountNo, transferAmount, refrenceNumber);
            Account latestAccount = accountService.findAccount(userAccount.getAccountNumber());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Fund Transfer Summary");
            System.out.println("Destination com.my.example.atm.dao.entity.Account : " + destinationAccountNo);
            System.out.println("Transfer Amount : " + transferAmount);
            System.out.println("Reference Number : " + refrenceNumber);
            System.out.println("Balance : " + latestAccount.getBalance());

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
