package view;

import entity.Account;
import service.AccountService;
import service.Utilities;

import java.util.Scanner;

public class WithdrawCustomScreen {

    private static final Long MAX_AMOUNT_WITHDRAWN_LIMIT = 1000L;

    private final AccountService accountService = new AccountService();
    private final WithdrawSummaryScreen withdrawSummaryScreen = new WithdrawSummaryScreen();

    public Integer showWithdrawCustomAmountScreen(Account userAccount) {

        Integer withdrawSummaryResult = Utilities.RESET;
        while (isValidOnWithdrawCustomScreen(withdrawSummaryResult)) {
            try {
                Long customWithdrawAmount = validate(userAccount, showCustomWithdrawMenu());
                withdrawSummaryResult = withdrawSummaryScreen.showWithdrawSummaryScreen(userAccount, customWithdrawAmount);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return withdrawSummaryResult;
    }

    private boolean isValidOnWithdrawCustomScreen(Integer withdrawSummaryResult) {
        return withdrawSummaryResult.equals(Utilities.RESET);
    }

    private Long validate(Account userAccount, String customWithdraw) throws Exception {

        boolean isMaxValid;
        boolean isMultipleByTenValid;

        if (!Utilities.isNumber(customWithdraw)) {
            throw new Exception("Amount input should only contains number!" + "\n");
        }

        Long customWithdrawAmount = Long.valueOf(customWithdraw);
        isMaxValid = customWithdrawAmount <= MAX_AMOUNT_WITHDRAWN_LIMIT;
        isMultipleByTenValid = (customWithdrawAmount % 10 == 0);
        if (!isMaxValid || !isMultipleByTenValid) {
            throw new Exception(!isMaxValid ? "Maximum amount to withdraw is $1000!" + "\n" : "Invalid amount!" + "\n");
        }

        if (!accountService.validateWithdrawTransaction(userAccount, customWithdrawAmount)) {
            throw new Exception("Insufficient balance $" + customWithdrawAmount + "!\n");
        }
        return customWithdrawAmount;
    }

    private static String showCustomWithdrawMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Other Withdraw");
        System.out.println("Enter amount to withdraw:");
        return scanner.nextLine();
    }

}
