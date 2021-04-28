package view;

import entity.Account;
import service.Utilities;

import java.util.Scanner;

public class TransactionScreen {

    /*Transaction Screen Constant*/
    private static final String WITHDRAWN_MENU = "1";
    private static final String TRANSFER_MENU = "2";
    private static final String EXIT_MENU = "3";

    private final WithdrawScreen withdrawScreen = new WithdrawScreen();
    private final TransferScreen transferScreen = new TransferScreen();

    public void launchTransactionScreen(Account userAccount){
        String transactionScreenInput = "";
        Integer transactionResult = Utilities.SUMMARY_RESET_MENU;

        while (!transactionScreenInput.equals(EXIT_MENU) &&
                !transactionResult.equals(Utilities.SUMMARY_EXIT_MENU)){

            transactionScreenInput = showTransactionMenu();
            switch (transactionScreenInput){
                case WITHDRAWN_MENU:
                    transactionResult = withdrawScreen.lauchWithdrawScreen(userAccount);
                    break;
                case TRANSFER_MENU:
                    transactionResult = transferScreen.launchFundTransferScreen(userAccount);
                    break;
                case EXIT_MENU:
                case "":
                    transactionScreenInput = EXIT_MENU;
                    break;
            }
        }
    }

    private String showTransactionMenu() {
        /*Show the menu*/
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.println("Please choose option[3]:");
        return scanner.nextLine();
    }
}
