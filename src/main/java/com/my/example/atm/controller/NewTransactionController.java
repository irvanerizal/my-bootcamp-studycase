package com.my.example.atm.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class NewTransactionController {

    private static final String WITHDRAWN_MENU = "1";
    private static final String TRANSFER_MENU = "2";
    private static final String TRANSACTION_HISTORY_MENU = "3";
    private static final String EXIT_MENU = "4";

    @GetMapping("app/transaction")
    public String transaction() {
        return "transaction-page";
    }

    @PostMapping("app/transaction")
    public String transaction(@RequestParam String menuInput,
                              Model model,
                              HttpSession session) {
        switch (menuInput) {
            case WITHDRAWN_MENU:
                return "redirect:/app/withdraw";

            case TRANSFER_MENU:
                return "redirect:/app/transfer";

            case TRANSACTION_HISTORY_MENU:
                return "redirect:/app/transactionhistory?page=1&size=10";

            case EXIT_MENU:
                SecurityContextHolder.getContext().setAuthentication(null);
//                session.setComplete();
                session.invalidate();
                return "redirect:/welcome";

            default:
                return "redirect:/app/transaction";
        }
    }


}
