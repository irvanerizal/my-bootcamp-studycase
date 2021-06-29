package com.my.example.atm.cli;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show withdraw menu screen
 *
 * */
@Component
public class WithdrawScreen {

    /*Withdrawn Screen Constant*/
    private static final Long AMOUNT_10 = 10L;
    private static final Long AMOUNT_50 = 50L;
    private static final Long AMOUNT_100 = 100L;
    private static final String WITHDRAWN_10_MENU = "1";
    private static final String WITHDRAWN_50_MENU = "2";
    private static final String WITHDRAWN_100_MENU = "3";
    private static final String WITHDRAWN_CUSTOM_MENU = "4";
    private static final String BACK_MENU = "5";

    @Autowired
    private WithdrawSummaryScreen withdrawSummaryScreen;
    @Autowired
    private WithdrawCustomScreen withdrawCustomService;

    public Integer lauchWithdrawScreen(Account userAccount) {
        Integer transactionResult = Utilities.RESET;

        while (isValidOnWithdrawMenu(transactionResult)) {
            switch (showWithdrawMenu()) {
                case WITHDRAWN_10_MENU:
                    transactionResult = withdrawSummaryScreen.showWithdrawSummaryScreen(userAccount, AMOUNT_10);
                    break;
                case WITHDRAWN_50_MENU:
                    transactionResult = withdrawSummaryScreen.showWithdrawSummaryScreen(userAccount, AMOUNT_50);
                    break;
                case WITHDRAWN_100_MENU:
                    transactionResult = withdrawSummaryScreen.showWithdrawSummaryScreen(userAccount, AMOUNT_100);
                    break;
                case WITHDRAWN_CUSTOM_MENU:
                    transactionResult = withdrawCustomService.showWithdrawCustomAmountScreen(userAccount);
                    break;
                case BACK_MENU:
                case "":
                    transactionResult = Utilities.BACK_TRANSACTION_MENU;
                    break;
            }
        }
        return transactionResult;
    }

    private boolean isValidOnWithdrawMenu(Integer withdrawSummaryResult) {
        return withdrawSummaryResult.equals(Utilities.RESET);
    }

    private String showWithdrawMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        return scanner.nextLine();
    }
}
