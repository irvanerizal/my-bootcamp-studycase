package com.my.example.atm.controller;

import com.my.example.atm.dao.dto.TransferRequest;
import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.Utilities;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class NewTransferController {

    private static final String CONFIRM_TRX = "1";
    private static final String CANCEL_TRX = "2";
    private static final String TRANSACTION_MENU = "1";
    private static final String EXIT_MENU = "2";

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    @GetMapping("app/transfer")
    private String transfer(ModelMap model, HttpSession session) {
        return "transfer-page";
    }

    @PostMapping("app/transfer")
    public String transfer(@ModelAttribute TransferRequest request,
                           ModelMap model,
                           HttpSession session) {
        try {
            transferService.isAccountValid(request.getDestAccountNumber());
            transferService.isAmountValid(request.getAmount());

            return "redirect:/app/transferconfirmation?destAccountNumber=" + request.getDestAccountNumber() +
                    "&transferAmount=" + request.getAmount();

        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "transfer-page";
        }
    }

    @RequestMapping(value = "/app/transferconfirmation", method = RequestMethod.GET)
    public String transferConfirmation(@RequestParam String destAccountNumber,
                                       @RequestParam String transferAmount,
                                       ModelMap model) {
        Integer refNumber = Utilities.generateRefNumber();

        model.put("destination", destAccountNumber);
        model.put("amount", transferAmount);
        model.put("referenceNumber", refNumber);

        return "transfer-confirmation-page";
    }

    @PostMapping("app/transferconfirmation")
    public String transferConfirmation(ModelMap model, @RequestParam String destination, @RequestParam String amount,
                                       @RequestParam String referenceNumber,
                                       @RequestParam String confirmInput,
                                       HttpSession session) {
        try {
            switch (confirmInput) {
                case CONFIRM_TRX:
                    Account loggedAcc = (Account) session.getAttribute(ACCOUNT_KEY);

                    transferService.transfer(loggedAcc, destination, amount, referenceNumber);
                    Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());

                    return "redirect:/app/transfersummary?destination=" + destination +
                            "&amount=" + amount +
                            "&refNumber=" + referenceNumber +
                            "&balance=" + latestAccount.getBalance();
                case CANCEL_TRX:
                default:
                    return "redirect:/app/transaction";
            }
        } catch (Exception e) {
            model.put("errorMessage", e.getMessage());
            return "transfer-confirmation-page";
        }
    }

    @RequestMapping(value = "/app/transfersummary", method = RequestMethod.GET)
    public String summary(@RequestParam String destination,
                          @RequestParam String amount,
                          @RequestParam String refNumber,
                          @RequestParam String balance,
                          ModelMap model,
                          HttpSession session) {

        model.put("destination", destination);
        model.put("amount", amount);
        model.put("referencenumber", refNumber);
        model.put("balance", balance);

        return "transfer-summary-page";
    }

    @PostMapping("app/transfersummary")
    public String summary(@RequestParam String summaryInput,
                          HttpSession session) {
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
}
