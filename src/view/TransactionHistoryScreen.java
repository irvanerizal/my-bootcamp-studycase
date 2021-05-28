package view;

import entity.Account;
import entity.Transaction;
import service.TransactionService;
import service.Utilities;

import java.util.List;

/**
 * This class has the responsibility to show the last transaction histories of the login user
 *
 * */
public class TransactionHistoryScreen {

    private final TransactionService transactionService = new TransactionService();

    public void showTransactionHistoriesScreen(Account userAccount) {
        System.out.println("User Latest Balance : " + userAccount.getBalance());
        System.out.println("*** User Transaction Histories ***");
        List<Transaction> userTransactions = transactionService.getTransactionByUserAccount(userAccount.getAccountNumber());
        userTransactions.forEach(transaction -> System.out.println(formattingTransactionHistories(transaction)));
    }

    private String formattingTransactionHistories(Transaction transaction){
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction Type : ").append(transaction.getType()).append("\n");
        sb.append("Created By : ").append(transaction.getCreatedBy()).append("\n");
        sb.append("Date Time : ").append(transaction.getDateTime().format(Utilities.DATE_FORMATTER)).append("\n");

        if (transaction.getType().equals(Transaction.TransactionType.WITHDRAW)) {
            Transaction.Withdraw withdraw = (Transaction.Withdraw) transaction;
            sb.append("Amount : ").append(withdraw.getAmount()).append("\n");
        } else {
            Transaction.Transfer transfer = (Transaction.Transfer) transaction;
            sb.append("Origin Sender Acc : ").append(transfer.getOriginAccountNo()).append("\n");
            sb.append("Destination Receiver Acc : ").append(transfer.getDestinationAccountNo()).append("\n");
            sb.append("Refrence Number : ").append(transfer.getRefrenceNumber()).append("\n");
            sb.append("Transfer Amount : ").append(transfer.getAmount()).append("\n");
        }
        sb.append("--- *** ---");
        return sb.toString();
    }
}
