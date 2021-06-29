package com.my.example.atm.controller;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.dao.entity.Transaction;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.api.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

@Controller
public class TransactionHistoryController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping("transactionhistory")
    public String transactionhistory(ModelMap model,
                           HttpServletRequest request){
        try{
            if(request.getSession(false) == null){
                return "welcome";
            }

            Account loggedAcc = (Account)request.getSession().getAttribute(ACCOUNT_KEY);
            Account latestAccount = accountService.findAccount(loggedAcc.getAccountNumber());

            List<Transaction> userTransactions = transactionService.getTransactionByUserAccount(loggedAcc.getAccountNumber());
            model.addAttribute("balance", latestAccount.getBalance());
            model.addAttribute("transaction", userTransactions);
            return "transactionhistory";
        }catch (Exception e){
            model.put("errorMessage", e.getMessage());
            return "transactionhistory";
        }
    }
}
