import view.WelcomeScreen;

public class Main {

//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private static AccountService accountService = new AccountService();

    private static WelcomeScreen welcomeScreen = new WelcomeScreen();

    public static void main(String[] args) {
        while (true) {
            // do something)
            welcomeScreen.launchWelcomeScreen();
        }
    }

    /*private static void launchWelcomeScreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter entity.Account Number : ");
        String accountNo = scanner.next();
        boolean accLengthValidation = isAccLengthValidation(accountNo.length(), 6); // TODO: 26/04/2021 put as constant
        boolean accNumberValidation = isNumber(accountNo);

        if (!isAccLengthValidation(accountNo.length(), 6) || !isNumber(accountNo)) {
            if (!accLengthValidation) {
                System.out.println("entity.Account Number should have 6 digits length");
            } else {
                System.out.println("entity.Account Number should only contains number");
            }
            return ;
        }
        System.out.println("Enter PIN : " + scanner.nextLine());
        String pin = scanner.next();
        boolean pinLengthValidation = isAccLengthValidation(pin.length(), 6);
        boolean pinNumberValidation = isNumber(pin);

        Account userAccount = accountService.validateAccount(accountNo, pin);
        // TODO: 26/04/2021 Need to refactor it, access the db after validation

        if (pinLengthValidation && pinNumberValidation) {
            if (userAccount == null) {
                System.out.println("Invalid entity.Account Number/PIN");
            } else {
                System.out.println("Welcome " + userAccount.getName());
                launchTransactionScreen(userAccount);
            }
        } else {
            if (!pinLengthValidation) {
                System.out.println("PIN should have 6 digits length");
            } else {
                System.out.println("PIN should only contains number");
            }
        }
    }

    private static boolean isAccLengthValidation(long length, int i) {
        return length == i;
    }

    private static void launchTransactionScreen(Account userAccount){

        // TODO: 26/04/2021 Replace the screen menu logic
        String transactionMenu = "init";
        Integer withdrawSummaryMenu = 0;
        Integer transferSummaryMenu = 0;
        while (!transactionMenu.isEmpty() && !transactionMenu.equals("3")
                && withdrawSummaryMenu!=2 && transferSummaryMenu!=2){
            transactionMenu = showTransactionMenu();
            switch (transactionMenu){
                case "1":
                    withdrawSummaryMenu = lauchWithdrawScreen(userAccount);
                    break;
                case "2":
                    transferSummaryMenu = launchFundTransferScreen(userAccount);
                    break;
                case "3":
                case "":
                    break;
            }
        }
    }

    private static Integer lauchWithdrawScreen(Account userAccount){
        String withdrawInput = "0";
        String summaryInput = "0";

        while ((!withdrawInput.equals("5") && !withdrawInput.isEmpty())
                && summaryInput.equals("0")){
            withdrawInput = showWithdrawMenu();
            switch (withdrawInput){
                case "1":
                    summaryInput = withdraw(userAccount, summaryInput, 10l);
                    break;
                case "2":
                    summaryInput = withdraw(userAccount, summaryInput, 50l);
                    break;
                case "3":
                    summaryInput = withdraw(userAccount, summaryInput, 100l);
                    break;
                case "4":
                    boolean isNumberValidation = false;
                    boolean isMaxAmountValidation = false;
                    boolean isTenMultipleValidation = false;
                    boolean withdrawValidation = false;

                    while (!isNumberValidation || !isMaxAmountValidation
                            || !isTenMultipleValidation || !withdrawValidation){

                        String customWithdraw = showCustomWithdrawMenu();
                        isNumberValidation = isNumber(customWithdraw);

                        if(isNumberValidation){
                            Long customWithdrawAmount = Long.valueOf(customWithdraw);
                            isMaxAmountValidation = customWithdrawAmount < 1000;
                            isTenMultipleValidation = (customWithdrawAmount % 10 == 0);

                            if(isMaxAmountValidation && isTenMultipleValidation){
                                withdrawValidation = validateWithdrawTransaction(userAccount, customWithdrawAmount);

                                summaryInput = withdraw(userAccount, summaryInput, customWithdrawAmount);
                            } else {
                                if(!isMaxAmountValidation){
                                    System.out.println("Maximum amount to withdraw is $1000");
                                } else {
                                    System.out.println("Invalid amount");
                                }
                            }
                        } else {
                            System.out.println("Amount input should only contains number");
                        }
                    }
                    break;
                case "5":
                case "":
                    break;
            }
        }
        return summaryInput.isEmpty()? 2 : Integer.parseInt(summaryInput);
    }

    private static String withdraw(Account userAccount, String summaryInput, long l) {
        if (!validateWithdrawTransaction(userAccount, l)) {
            System.out.println("Insufficient balance $" + l);
            return summaryInput;
        }
        deductUserBalance(userAccount, l);
        summaryInput = showSummaryMenu(userAccount, l);
        return summaryInput;
    }

    private static Integer launchFundTransferScreen(Account userAccount) {
        String destinationAccInput = "init";
        String transferAmountInput = "init";
        String summaryInput = "0";

        boolean isDestinationAccValid = false;
        boolean isTransferAmountValid = false;
        int refNumber;

        while (!destinationAccInput.isEmpty() && !transferAmountInput.isEmpty()
                && summaryInput.equals("0")){

            String confirmInput = "init";

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter destination account and press enter to continue or \n" +
                    "press enter to go back to Transaction:");
            destinationAccInput = scanner.nextLine();

            if(!destinationAccInput.isEmpty()){
                isDestinationAccValid = validateUserAccount(destinationAccInput);
            }
            scanner.reset();

            while (isDestinationAccValid && !transferAmountInput.isEmpty()
                    && (!confirmInput.isEmpty() && !confirmInput.equals("2"))
                    && summaryInput.equals("0")){

                System.out.println("Please enter transfer amount and \n" +
                        "press enter to continue or \n" +
                        "press enter to go back to Transaction:");
                transferAmountInput = scanner.nextLine();

                if(!transferAmountInput.isEmpty()){
                    isTransferAmountValid = validateTransferAmount(userAccount, transferAmountInput);
                }
                scanner.reset();

                while (isTransferAmountValid
                        && (!confirmInput.isEmpty() && !confirmInput.equals("2"))
                        && summaryInput.equals("0")){

                    refNumber = generateRefNumber();
                    System.out.println("Reference Number: " + refNumber + "\n" +
                            "press enter to continue");
                    scanner.nextLine(); scanner.reset();

                    System.out.println("Transfer Confirmation");
                    System.out.println("Destination entity.Account : " + destinationAccInput);
                    System.out.println("Transfer Amount : " + transferAmountInput.toString());
                    System.out.println("Reference Number : " + refNumber);

                    System.out.println("1. Confirm Trx");
                    System.out.println("2. Cancel Trx");
                    System.out.println("Choose option[2]");

                    confirmInput = scanner.nextLine();
                    switch (confirmInput){
                        case "1":
                            deductUserBalance(userAccount, Long.valueOf(transferAmountInput));
                            addUserBalance(accountService.findAccount(destinationAccInput), Long.valueOf(transferAmountInput));

                            summaryInput = showFundSummaryMenu(userAccount, destinationAccInput, Long.valueOf(transferAmountInput), Integer.toString(refNumber));
                            break;
                        case "2":
                        case "":
                            destinationAccInput = "init";
                            transferAmountInput = "init";
                            isDestinationAccValid = false;
                            isTransferAmountValid = false;
                            break;
                    }
                }
            }
        }
        return summaryInput.isEmpty()? 2 : Integer.parseInt(summaryInput);
    }


    private static boolean validateUserAccount(String destinationAccountNo){
        if(destinationAccountNo.isEmpty() || !isNumber(destinationAccountNo) ||
                accountService.findAccount(destinationAccountNo) == null){
            System.out.println("Invalid entity.Account");
            return false;
        }
        return true;
    }

    private static boolean validateTransferAmount(Account userAccount, String transferAmount){
        if(!isNumber(transferAmount)){
            System.out.println("Amount input should only contains number");
            return false;
        } else if(Long.parseLong(transferAmount) > 1000l){
            System.out.println("Maximum amount to withdraw is $1000");
            return false;
        } else if(Long.parseLong(transferAmount) < 1l){
            System.out.println("Minimum amount to withdraw is $1");
            return false;
        } else if(userAccount.getBalance() - Long.parseLong(transferAmount) < 0){
            System.out.println("Insufficient balance $" + transferAmount);
            return false;
        }
        return true;
    }

    private static Boolean validateWithdrawTransaction(Account userAccount, Long withdrawAmount){
        return (userAccount.getBalance() >= withdrawAmount
                && userAccount.getBalance() - withdrawAmount >= 0);
    }

    private static void deductUserBalance(Account userAccount, Long withdrawAmount){
        Long newBalance = userAccount.getBalance() - withdrawAmount;
        userAccount.setBalance(newBalance);
    }

    private static void addUserBalance(Account userAccount, Long transferAmount){
        Long newBalance = userAccount.getBalance() + transferAmount;
        userAccount.setBalance(newBalance);
    }

    *//*Screen Menus*//*
    private static String showTransactionMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Withdrawn");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.println("Please choose option[3]:");
        return scanner.nextLine();
    }

    private static String showWithdrawMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        return scanner.nextLine();
    }

    private static String showCustomWithdrawMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Other Withdraw");
        System.out.println("Enter amount to withdraw:");
        return scanner.nextLine();
    }

    private static String showSummaryMenu(Account userAccount, Long deduction) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Summary");
        System.out.println("Date : " + LocalDateTime.now().format(FORMATTER));
        System.out.println("Withdraw : " + deduction);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");
        return scanner.nextLine();
    }

    private static String showFundSummaryMenu(Account userAccount, String destinationAccountNo,
                                              Long transferAmount, String refrenceNumber) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fund Transfer Summary");
        System.out.println("Destination entity.Account : " + destinationAccountNo);
        System.out.println("Transfer Amount : " + transferAmount.toString());
        System.out.println("Reference Number : " + refrenceNumber);
        System.out.println("Balance : " + userAccount.getBalance());

        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]");
        return scanner.nextLine();
    }

    *//*Utils Methods*//*

    private static boolean isNumber(String input){
        return input.matches("[0-9]+");
    }

    private static Integer generateRefNumber(){
        return 100000 + new Random().nextInt(900000);
    }*/

    // TODO: Use for debugging only
//    private static void debuggingOnly(){
//        System.out.println("*DEBUGGING : User Latest Balance : " + userAccount.getBalance());
//        ACCOUNTS.forEach(account -> System.out.println(account.getName() + " - " + account.getBalance()));
//    }

}
