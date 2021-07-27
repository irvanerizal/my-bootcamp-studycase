package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.AccountUserDetails;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import static com.my.example.atm.service.Utilities.ACCOUNT_AND_PIN_LENGTH_VALIDATION;
import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class NewWelcomeController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value={"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome() {
        return "welcome-page";
    }

    @GetMapping("postLogin")
    public String welcome(Model model, HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == null) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
        Account loggedInUser = ((AccountUserDetails) authentication.getPrincipal()).getAccount();
        session.setAttribute(ACCOUNT_KEY, loggedInUser);
        return "redirect:app/transaction";
    }

    /** Old Controller*/
    /*
    @PostMapping("welcome")
    public String login(@ModelAttribute LoginRequest request,
                        Model model,
                        HttpSession session) {
        try {
            validateAccountNumber(request.getAccountNumber());
            validatePIN(request.getPin());

            Account userAccount = accountService.validateAccount(
                    request.getAccountNumber(), request.getPin());
            session.setAttribute(ACCOUNT_KEY, userAccount);

            return "redirect:app/transaction";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "welcome-page";
        }
    }*/

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
