package com.my.example.atm.cli;

import com.my.example.atm.dao.entity.Account;
import com.my.example.atm.service.api.AccountService;
import com.my.example.atm.service.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * This class has responsibility to show authentication screen
 * This will validate user credential input
 */
@Component
public class WelcomeScreen {

    private static final int INPUT_LENGTH_VALID = 6;

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionScreen transactionScreen;

    public void launchWelcomeScreen() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Account Number : ");
            String accountNo = scanner.next();
            validateAccountNumber(accountNo);

            System.out.println("Enter PIN : " + scanner.nextLine());
            String pin = scanner.next();
            validatePIN(pin);

            Account userAccount = accountService.validateAccount(accountNo, pin);
            System.out.println("Welcome " + userAccount.getName());
            transactionScreen.launchTransactionScreen(userAccount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Create different methods to handle PIN and AccNumb if the future PIN/AccountNumber policy change
     */
    private void validatePIN(String pin) throws Exception {
        if (!Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID) || !Utilities.isNumber(pin)) {
            throw new Exception(!Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID) ?
                    "PIN should have 6 digits length!" + "\n" : "PIN should only contains number!" + "\n");
        }
    }

    private void validateAccountNumber(String accountNo) throws Exception {
        if (!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) || !Utilities.isNumber(accountNo)) {
            throw new Exception(!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) ?
                    "Account Number should have 6 digits length!" + "\n" : "Account Number should only contains number!" + "\n");
        }

    }

}
