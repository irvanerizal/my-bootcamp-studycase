package com.my.example.atm.view;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show withdraw custom menu screen
 */
@Component
public class WithdrawCustomScreen {

    private static final Long MAX_AMOUNT_WITHDRAWN_LIMIT = 1000L;

    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private WithdrawSummaryScreen withdrawSummaryScreen;

    public Integer showWithdrawCustomAmountScreen(Account userAccount) {

        Integer transactionResult = Utilities.RESET;
        while (isValidOnWithdrawCustomScreen(transactionResult)) {
            try {
                Long customWithdrawAmount = validate(userAccount, showCustomWithdrawMenu());
                transactionResult = withdrawSummaryScreen.showWithdrawSummaryScreen(userAccount, customWithdrawAmount);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return transactionResult;
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

        if (!withdrawService.validateWithdrawTransaction(userAccount, customWithdrawAmount)) {
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
