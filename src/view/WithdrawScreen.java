package view;

import entity.Account;
import service.Utilities;
import service.WithdrawService;

import java.util.Scanner;

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

    private final WithdrawService withdrawService = new WithdrawService();
    private final WithdrawCustomScreen withdrawCustomService = new WithdrawCustomScreen();

    public Integer lauchWithdrawScreen(Account userAccount){
        String withdrawScreenInput = "";
        Integer withdrawSummaryResult = Utilities.SUMMARY_INPUT_TO_RESET;

        while (!withdrawScreenInput.equals(BACK_MENU) &&
                withdrawSummaryResult.equals(Utilities.SUMMARY_INPUT_TO_RESET)){

            withdrawScreenInput = showWithdrawMenu();
            switch (withdrawScreenInput){
                case WITHDRAWN_10_MENU:
                    withdrawSummaryResult = withdrawService.withdraw(userAccount, AMOUNT_10);
                    break;
                case WITHDRAWN_50_MENU:
                    withdrawSummaryResult = withdrawService.withdraw(userAccount, AMOUNT_50);
                    break;
                case WITHDRAWN_100_MENU:
                    withdrawSummaryResult = withdrawService.withdraw(userAccount, AMOUNT_100);
                    break;
                case WITHDRAWN_CUSTOM_MENU:
                    withdrawSummaryResult = withdrawCustomService.withdrawCustomAmount(userAccount);
                    break;
                case BACK_MENU:
                case "":
                    withdrawScreenInput=BACK_MENU;
                    break;
            }
        }
        return withdrawSummaryResult;
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
