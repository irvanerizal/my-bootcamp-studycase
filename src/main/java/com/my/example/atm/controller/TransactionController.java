package com.my.example.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TransactionController {

    @GetMapping("transaction")
    public String transaction(ModelMap model, HttpServletRequest request){
        return checkSession(request, "transaction");
    }

    @GetMapping("exit")
    public String exit(ModelMap model, HttpServletRequest request){
        request.getSession().invalidate();
        return "welcome";
    }

    private String checkSession(HttpServletRequest request, String pageName){
        return request.getSession(false) == null ? "welcome" : pageName;
    }
}
