package com.my.example.atm.dao.dto;

import javax.validation.constraints.NotEmpty;

public class TransferRequest {

    @NotEmpty(message = "Account Number should not be empty")
    private String destAccountNumber;

    @NotEmpty(message = "PIN should not be empty")
    private String amount;

    public TransferRequest(String destAccountNumber, String amount) {
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;
    }

    public String getDestAccountNumber() {
        return destAccountNumber;
    }

    public void setDestAccountNumber(String destAccountNumber) {
        this.destAccountNumber = destAccountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
