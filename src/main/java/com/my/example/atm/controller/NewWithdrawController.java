package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;
import static com.my.example.atm.service.Utilities.MAX_AMOUNT_WITHDRAWN_LIMIT;

@Controller
public class NewWithdrawController {

    private static final Long AMOUNT_10 = 10L;
    private static final Long AMOUNT_50 = 50L;
    private static final Long AMOUNT_100 = 100L;
    private static final String WITHDRAWN_10_MENU = "1";
    private static final String WITHDRAWN_50_MENU = "2";
    private static final String WITHDRAWN_100_MENU = "3";
    private static final String WITHDRAWN_CUSTOM_MENU = "4";
    private static final String TRANSACTION_MENU = "1";
    private static final String EXIT_MENU = "2";

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private AccountService accountService;

    @GetMapping("app/withdraw")
    public String withdraw() {
        return "withdraw-page";
    }

    @PostMapping("app/withdraw")
    public String withdraw(ModelMap model,
                           @RequestParam String withdrawInput,
                           HttpSession session) {
        try {

            Account loggedAcc = (Account) session.getAttribute(ACCOUNT_KEY);
            switch (withdrawInput) {
                case WITHDRAWN_10_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_10);
                    return loadWithdrawSummary(loggedAcc, AMOUNT_10);

                case WITHDRAWN_50_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_50);
                    return loadWithdrawSummary(loggedAcc, AMOUNT_50);

                case WITHDRAWN_100_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_100);
                    return loadWithdrawSummary(loggedAcc, AMOUNT_100);

                case WITHDRAWN_CUSTOM_MENU:
                    return "redirect:/app/withdrawcustom";

                default:
                    return "redirect:/app/transaction";
            }
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "withdraw-page";
        }
    }

    @GetMapping("app/withdrawcustom")
    public String withdrawCustom() {
        return "withdraw-custom-page";
    }

    @PostMapping("app/withdrawcustom")
    public String withdrawCustom(ModelMap model, @RequestParam String withdrawInput,
                                 HttpSession session) {
        try {
            Account loggedAcc = (Account) session.getAttribute(ACCOUNT_KEY);
            Long customWithdrawAmount = validate(loggedAcc, withdrawInput);
            withdrawService.withdraw(loggedAcc, customWithdrawAmount);

            return loadWithdrawSummary(loggedAcc, customWithdrawAmount);
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "withdraw-custom-page";
        }
    }

    @RequestMapping(value = "/app/withdrawsummary", method = RequestMethod.GET)
    public String withdrawSummary(@RequestParam String amount,
                                  @RequestParam String balance,
                                  ModelMap model) {
        model.put("date", LocalDateTime.now().format(Utilities.DATE_FORMATTER));
        model.put("amount", amount);
        model.put("balance", balance);
        return "withdraw-summary-page";
    }

    @PostMapping("app/withdrawsummary")
    public String withdrawSummary(@RequestParam String summaryInput,
                                  HttpSession session){
        switch (summaryInput) {
            case EXIT_MENU:
                SecurityContextHolder.getContext().setAuthentication(null);
                session.invalidate();
                return "redirect:/welcome";
            case TRANSACTION_MENU:
            default:
                return "redirect:/app/transaction";
        }
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

    private String loadWithdrawSummary(Account loggedAcc, Long withdrawinput) throws Exception {
        Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());
        return "redirect:/app/withdrawsummary?amount=" + withdrawinput + "&balance=" + latestAccount.getBalance();
    }

}
