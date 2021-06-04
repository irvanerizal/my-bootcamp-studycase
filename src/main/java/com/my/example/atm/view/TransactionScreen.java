package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show transaction menu screen
 *
 * */
@Component
public class TransactionScreen {

    /*Transaction Screen Constant*/
    private static final String WITHDRAWN_MENU = "1";
    private static final String TRANSFER_MENU = "2";
    private static final String SHOW_TRANSACTION_HISTORY_MENU = "3";
    private static final String EXIT_MENU = "4";

    @Autowired
    private TransactionHistoryScreen transactionHistoryScreen;
    @Autowired
    private TransferScreen transferScreen;
    @Autowired
    private WithdrawScreen withdrawScreen;


    public void launchTransactionScreen(Account userAccount) {
        Integer transactionResult = Utilities.RESET;
        while (isValidOnTransactionMenu(transactionResult)) {
            switch (showTransactionMenu()) {
                case WITHDRAWN_MENU:
                    transactionResult = withdrawScreen.lauchWithdrawScreen(userAccount);
                    break;
                case TRANSFER_MENU:
                    transactionResult = transferScreen.launchFundTransferScreen(userAccount);
                    break;
                case SHOW_TRANSACTION_HISTORY_MENU:
                    transactionHistoryScreen.showTransactionHistoriesScreen(userAccount);
                    break;
                case EXIT_MENU:
                case "":
                    transactionResult = Utilities.EXIT_APP;
                    break;
            }
        }
    }

    private boolean isValidOnTransactionMenu(Integer transactionResult) {
        return !transactionResult.equals(Utilities.EXIT_APP);
    }

    private String showTransactionMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Show Latest Transactions");
        System.out.println("4. Exit");
        System.out.println("Please choose option[3]:");
        return scanner.nextLine();
    }
}
