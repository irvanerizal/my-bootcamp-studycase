package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.my.example.atm.service.Utilities.ACCOUNT_AND_PIN_LENGTH_VALIDATION;
import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class WelcomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("welcome")
    public String welcome(ModelMap model,
                          HttpServletRequest request) {
        request.getSession().invalidate();
        return "welcome";
    }

    @PostMapping("welcome")
    public String login(ModelMap model,
                        @RequestParam String account,
                        @RequestParam String pin,
                        HttpServletRequest request) {
        try {
            validateAccountNumber(account);
            validatePIN(pin);
            Account userAccount = accountService.validateAccount(account, pin);
            request.getSession().setAttribute(ACCOUNT_KEY, userAccount);
            return "transaction";
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "welcome";
        }
    }

    /**
     * Create different methods to handle PIN and AccNumb if the future PIN/AccountNumber policy change
     */
    private void validatePIN(String pin) throws Exception {
        if (!Utilities.isAccLengthValidation(pin.length(), ACCOUNT_AND_PIN_LENGTH_VALIDATION) || !Utilities.isNumber(pin)) {
            throw new Exception(!Utilities.isAccLengthValidation(pin.length(), ACCOUNT_AND_PIN_LENGTH_VALIDATION) ?
                    "PIN should have 6 digits length!" + "\n" : "PIN should only contains number!" + "\n");
        }
    }

    private void validateAccountNumber(String accountNo) throws Exception {
        if (!Utilities.isAccLengthValidation(accountNo.length(), ACCOUNT_AND_PIN_LENGTH_VALIDATION) || !Utilities.isNumber(accountNo)) {
            throw new Exception(!Utilities.isAccLengthValidation(accountNo.length(), ACCOUNT_AND_PIN_LENGTH_VALIDATION) ?
                    "Account Number should have 6 digits length!" + "\n" : "Account Number should only contains number!" + "\n");
        }
    }
}
