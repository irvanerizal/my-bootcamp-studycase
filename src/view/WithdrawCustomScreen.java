package view;

import entity.Account;
import service.AccountService;
import service.Utilities;
import service.WithdrawService;

import java.util.Scanner;

public class WithdrawCustomScreen {

    private AccountService accountService = new AccountService();
    private WithdrawService withdrawService = new WithdrawService();

    public Integer withdrawCustomAmount(Account userAccount){

        Integer withdrawSummaryResult = Utilities.SUMMARY_RESET_MENU;

        boolean isNumberValid;
        boolean isMaxValid;
        boolean isMultipleByTenValid;
        boolean withdrawValid;

        while (withdrawSummaryResult.equals(Utilities.SUMMARY_RESET_MENU)){

            String customWithdraw = showCustomWithdrawMenu();
            isNumberValid = Utilities.isNumber(customWithdraw);

            if (!isNumberValid) {
                System.out.println("Amount input should only contains number");
                continue;
            }
            Long customWithdrawAmount = Long.valueOf(customWithdraw);
            isMaxValid = customWithdrawAmount <= 1000;
            isMultipleByTenValid = (customWithdrawAmount % 10 == 0);

            if (!isMaxValid || !isMultipleByTenValid) {
                if(!isMaxValid){
                    System.out.println("Maximum amount to withdraw is $1000");
                } else {
                    System.out.println("Invalid amount");
               }
                continue;
            }
            withdrawValid = accountService.validateWithdrawTransaction(userAccount, customWithdrawAmount);
            if (!withdrawValid) {
                System.out.println("Insufficient balance $" + customWithdrawAmount);
                continue;
            }
            withdrawSummaryResult = withdrawService.withdraw(userAccount, customWithdrawAmount);
        }
        return withdrawSummaryResult;
    }

    private static String showCustomWithdrawMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Other Withdraw");
        System.out.println("Enter amount to withdraw:");
        return scanner.nextLine();
    }

}
