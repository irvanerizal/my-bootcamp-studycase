package view;

import entity.Account;
import service.AccountService;
import service.Utilities;

import java.util.Scanner;

public class TransferSummaryScreen {

    private static final Long MAX_AMOUNT_TRANSFER_LIMIT = 1000L;
    private static final Long MIN_AMOUNT_TRANSFER_LIMIT = 1L;

    private final AccountService accountService = new AccountService();

    public Integer showFundSummaryScreen(Account userAccount, String destinationAccountNo,
                                         Long transferAmount, String refrenceNumber) {
        try {
            transfer(userAccount, destinationAccountNo, transferAmount);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Fund Transfer Summary");
            System.out.println("Destination entity.Account : " + destinationAccountNo);
            System.out.println("Transfer Amount : " + transferAmount.toString());
            System.out.println("Reference Number : " + refrenceNumber);
            System.out.println("Balance : " + userAccount.getBalance());

            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.println("Choose option[2]");

            String input = scanner.nextLine();
            return input.isEmpty() ? Utilities.EXIT_APP :
                    !Utilities.isNumber(input) ? Utilities.EXIT_APP : Integer.parseInt(input);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Utilities.BACK_TRANSACTION_MENU;
        }
    }

    //Will be extracted as transfer service
    private void transfer(Account userAccount, String destinationAccount, Long transferAmount) throws Exception {
        if(!isTransferAmountValid(userAccount, transferAmount.toString())
                && !isTransferAmountValid(userAccount, transferAmount.toString())){
            return;
        }
        accountService.deductUserBalance(userAccount, transferAmount);
        accountService.addUserBalance(accountService.findAccount(destinationAccount), transferAmount);
    }

    public boolean isUserAccountValid(String destinationAccountNo) throws Exception {
        if(!destinationAccountNo.isEmpty() && !Utilities.isNumber(destinationAccountNo) &&
                null == accountService.findAccount(destinationAccountNo))
            throw new Exception("Invalid Account");
        return true;
    }

    public boolean isTransferAmountValid(Account userAccount, String transferAmount) throws Exception {
        if(!Utilities.isNumber(transferAmount))
            throw new Exception("Amount input should only contains number");
        if(Long.parseLong(transferAmount) > MAX_AMOUNT_TRANSFER_LIMIT)
            throw new Exception("Maximum amount to withdraw is $1000");
        if(Long.parseLong(transferAmount) < MIN_AMOUNT_TRANSFER_LIMIT)
            throw new Exception("Minimum amount to withdraw is $1");
        if(userAccount.getBalance() - Long.parseLong(transferAmount) < 0)
            throw new Exception("Insufficient balance $\" + transferAmount");
        return true;
    }
}
