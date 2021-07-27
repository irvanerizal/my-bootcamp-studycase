package com.my.example.atm.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.my.example.atm.service.Utilities.ACCOUNT_KEY;

/**
 * JSP Controller
 * */
//@Controller
public class TransactionController {

    @GetMapping("transaction")
    @PostMapping("transaction")
    public String transaction(ModelMap model, HttpServletRequest request){
        return checkSession(request, "transaction");
    }

    @GetMapping("exit")
    public String exit(ModelMap model, HttpServletRequest request){

        request.getSession().invalidate();
        return "welcome";
    }

    private String checkSession(HttpServletRequest request, String pageName){
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute(ACCOUNT_KEY) != null){
            //do something
            return pageName;
        }
        return "welcome";
        //        return request.getSession(false) == null ? "welcome" : pageName;
    }
}
