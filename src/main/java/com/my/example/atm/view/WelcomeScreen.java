package com.my.example.atm.view;

import com.my.example.atm.entity.Account;
import com.my.example.atm.service.AccountService;
import com.my.example.atm.service.Utilities;

import java.util.Scanner;

/**
 * This class has responsibility to show authentication screen
 * This will validate user credential input
 *
 * */
public class WelcomeScreen {

    private static final int INPUT_LENGTH_VALID = 6;

    private final TransactionScreen transactionScreen = new TransactionScreen();
    private final AccountService accountService = new AccountService();

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
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Create different methods to handle PIN and AccNumb if the future PIN/AccountNumber policy change
    * */
    private void validatePIN(String pin) throws Exception {
        if(!Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID) || !Utilities.isNumber(pin)){
            throw new Exception(!Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID) ?
                    "PIN should have 6 digits length!" + "\n" : "PIN should only contains number!" + "\n");
        }
    }

    private void validateAccountNumber(String accountNo) throws Exception {
        if(!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) || !Utilities.isNumber(accountNo)){
            throw new Exception(!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) ?
                    "Account Number should have 6 digits length!" + "\n" : "Account Number should only contains number!" + "\n");
        }

    }

}
