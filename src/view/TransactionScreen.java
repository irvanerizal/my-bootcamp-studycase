package view;

import entity.Account;
import service.Utilities;

import java.util.Scanner;

public class TransactionScreen {

    /*Transaction Screen Constant*/
    private static final String WITHDRAWN_MENU = "1";
    private static final String TRANSFER_MENU = "2";
    private static final String SHOW_TRANSACTION_HISTORY_MENU = "3";
    private static final String EXIT_MENU = "4";

    private final WithdrawScreen withdrawScreen = new WithdrawScreen();
    private final TransferScreen transferScreen = new TransferScreen();
    private final TransactionHistoryScreen transactionHistoryScreen = new TransactionHistoryScreen();

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
