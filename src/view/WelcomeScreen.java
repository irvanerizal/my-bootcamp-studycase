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
            System.out.println(!Utilities.isAccLengthValidation(accountNo.length(), INPUT_LENGTH_VALID) ?
                    "Account Number should have 6 digits length!" + "\n" : "Account Number should only contains number!" + "\n");
            return ;
        }

        System.out.println("Enter PIN : " + scanner.nextLine());
        String pin = scanner.next();
        boolean pinLengthValidation = Utilities.isAccLengthValidation(pin.length(), INPUT_LENGTH_VALID);
        boolean pinNumberValidation = Utilities.isNumber(pin);
        if (!pinLengthValidation || !pinNumberValidation) {
            System.out.println(!pinLengthValidation ?
                    "PIN should have 6 digits length!" + "\n" : "PIN should only contains number!" + "\n");
            return;
        }

        Account userAccount = accountService.validateAccount(accountNo, pin);
        if (userAccount == null) {
            System.out.println("Invalid Account Number/PIN!"+"\n");
            return;
        }
        System.out.println("Welcome " + userAccount.getName());
        transactionScreen.launchTransactionScreen(userAccount);
    }

}
