package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.AccountService;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * This class has responsibility to show withdraw summary menu screen
 * This will do the withdraw action
 * */
@Component
public class WithdrawSummaryScreen {

    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private AccountService accountService;

    public Integer showWithdrawSummaryScreen(Account userAccount, Long withdrawnAmount)  {

        try {
            withdrawService.withdraw(userAccount, withdrawnAmount);
            Account latestAccount = accountService.findAccount(userAccount.getAccountNumber());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Summary");
            System.out.println("Date : " + LocalDateTime.now().format(Utilities.DATE_FORMATTER));
            System.out.println("Withdraw : " + withdrawnAmount);
            System.out.println("Balance : " + latestAccount.getBalance());

            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.println("Choose option[2]");
            String input = scanner.nextLine();

            return input.isEmpty() ? Utilities.BACK_TRANSACTION_MENU :
                    !Utilities.isNumber(input) ? Utilities.BACK_TRANSACTION_MENU : Integer.parseInt(input);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Utilities.RESET;
        }
    }


}
