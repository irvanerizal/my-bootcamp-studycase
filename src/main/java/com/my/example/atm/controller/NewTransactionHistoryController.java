package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class NewTransactionHistoryController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping("app/transactionhistory")
    public String transactionhistory(ModelMap model,
                                     @RequestParam String page,
                                     @RequestParam String size,
                                     HttpSession session){
        try{
            Account loggedAcc = (Account)session.getAttribute(ACCOUNT_KEY);
            Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());

            Page<Transaction> userTransactions = transactionService.getTransactionPageByUserAccount(loggedAcc.getAccountNumber(),
                    PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(size)));
            model.addAttribute("balance", latestAccount.getBalance());
            model.addAttribute("transactions", userTransactions);

            return "transaction-history-pagination-page";
        }catch (Exception e){
            model.put("errorMessage", e.getMessage());

            return "transaction-history-pagination-page";
        }
    }

}
