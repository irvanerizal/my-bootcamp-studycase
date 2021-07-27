package com.my.example.atm.dao.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

// TODO: Delete this class if unused in the future
public class LoginRequest {

    @NotEmpty(message = "Account Number should not be empty")
    @Min(6)
    private String accountNumber;

    @NotEmpty(message = "PIN should not be empty")
    @Min(6)
    private String pin;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
