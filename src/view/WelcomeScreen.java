package view;

import entity.Account;
import service.AccountService;
import service.Utilities;

import java.util.Scanner;

public class WelcomeScreen {

    private static final int INPUT_LENGTH_VALID = 6;

    private final TransactionScreen transactionScreen = new TransactionScreen();
    private final AccountService accountService = new AccountService();

    public void launchWelcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Account Number : ");
        String accountNo = scanner.next();
        if (!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) || !Utilities.isNumber(accountNo)) {
            if (!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID)) {
                System.out.println("Account Number should have 6 digits length");
            } else {
                System.out.println("Account Number should only contains number");
            }
            return ;
        }

        System.out.println("Enter PIN : " + scanner.nextLine());
        String pin = scanner.next();
        boolean pinLengthValidation = Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID);
        boolean pinNumberValidation = Utilities.isNumber(pin);
        if (!pinLengthValidation || !pinNumberValidation) {
            if (!pinLengthValidation) {
                System.out.println("PIN should have 6 digits length");
            } else {
                System.out.println("PIN should only contains number");
            }
            return;
        }

        Account userAccount = accountService.validateAccount(accountNo, pin);
        if (userAccount == null) {
            System.out.println("Invalid Account Number/PIN");
            return;
        }
        System.out.println("Welcome " + userAccount.getName());
        transactionScreen.launchTransactionScreen(userAccount);
    }

}
