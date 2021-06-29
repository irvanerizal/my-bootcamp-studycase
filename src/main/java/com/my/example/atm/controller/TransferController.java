package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class TransferController {

    private static final String CONFIRM_TRX = "1";
    private static final String CANCEL_TRX = "2";
    private static final String TRANSACTION_MENU = "1";
    private static final String EXIT_MENU = "2";

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    @GetMapping("transfer")
    private String transfer(ModelMap model, HttpServletRequest request) {
        return checkSession(request, "transfer");
    }

    @PostMapping("transfer")
    public String doTransfer(ModelMap model, @RequestParam String destinationinput, @RequestParam String transferamount,
                             HttpServletRequest request) {
        try {
            Integer refNumber = Utilities.generateRefNumber();
            transferService.isAccountValid(destinationinput);
            transferService.isAmountValid(transferamount);

            model.put("destination", destinationinput);
            model.put("amount", transferamount);
            model.put("referencenumber", refNumber);
            return "transferconfirmation";

        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "transfer";
        }
    }

    @PostMapping("transferconfirmation")
    public String transferConfirmation(ModelMap model, @RequestParam String destination, @RequestParam String amount,
                                       @RequestParam String referencenumber,
                                       @RequestParam String confirminput,
                                       HttpServletRequest request) {
        try {
            switch (confirminput) {
                case CONFIRM_TRX:
                    Account loggedAcc = (Account)request.getSession().getAttribute(ACCOUNT_KEY);

                    transferService.transfer(loggedAcc, destination, amount, referencenumber);
                    Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());

                    model.put("destination", destination);
                    model.put("amount", amount);
                    model.put("referencenumber", referencenumber);
                    model.put("balance", latestAccount.getBalance());

                    return "transfersummary";
                case CANCEL_TRX:
                default:
                    return "transaction";
            }
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "transferconfirmation";
        }
    }

    @PostMapping("transfersummary")
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

    private String checkSession(HttpServletRequest request, String pageName){
        return request.getSession(false) == null ? "welcome" : pageName;
    }
}
