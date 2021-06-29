package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;
import static com.my.example.atm.service.Utilities.MAX_AMOUNT_WITHDRAWN_LIMIT;

@Controller
public class WithdrawController {

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

    @GetMapping("withdraw")
    public String withdraw(ModelMap model, HttpServletRequest request){
        return checkSession(request, "withdraw");
    }

    @PostMapping("withdraw")
    public String withdraw(ModelMap model, @RequestParam String withdrawinput,
                           HttpServletRequest request) {
        try {
            Account loggedAcc = (Account)request.getSession().getAttribute(ACCOUNT_KEY);
            switch (withdrawinput) {
                case WITHDRAWN_10_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_10);
                    return loadWithdrawSummary(loggedAcc, model, AMOUNT_10);
                case WITHDRAWN_50_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_50);
                    return loadWithdrawSummary(loggedAcc, model, AMOUNT_50);
                case WITHDRAWN_100_MENU:
                    withdrawService.withdraw(loggedAcc, AMOUNT_100);
                    return loadWithdrawSummary(loggedAcc, model, AMOUNT_100);
                case WITHDRAWN_CUSTOM_MENU:
                    return "withdrawcustom";
                default:
                    return "transaction";
            }
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "withdraw";
        }
    }

    @PostMapping("withdrawcustom")
    public String withdrawCustom(ModelMap model, @RequestParam String custominput,
                                 HttpServletRequest request){
        try{
            Account loggedAcc = (Account)request.getSession().getAttribute(ACCOUNT_KEY);
            Long customWithdrawAmount = validate(loggedAcc, custominput);
            withdrawService.withdraw(loggedAcc, customWithdrawAmount);

            return loadWithdrawSummary(loggedAcc, model, customWithdrawAmount);
        }
        catch (Exception e){
            model.put("errorMessage", e.getMessage());
            return "withdrawcustom";
        }
    }

    @PostMapping("withdrawsummary")
    public String summary(ModelMap model, @RequestParam String summaryinput,
                          HttpServletRequest request){
        switch (summaryinput) {
            case EXIT_MENU:
                request.getSession().invalidate();
                return "welcome";
            case TRANSACTION_MENU:
            default:
                return "transaction";
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

    private String loadWithdrawSummary(Account loggedAcc, ModelMap model, Long withdrawinput) throws Exception {
        Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());
        model.put("date", LocalDateTime.now().format(Utilities.DATE_FORMATTER));
        model.put("amount", withdrawinput);
        model.put("balance", latestAccount.getBalance());

        return "withdrawsummary";
    }

    private String checkSession(HttpServletRequest request, String pageName){
        return request.getSession(false) == null ? "welcome" : pageName;
    }
}
