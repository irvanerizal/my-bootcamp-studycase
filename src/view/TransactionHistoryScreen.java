package view;

import entity.Account;
import entity.Transaction;
import service.TransactionService;

import java.util.Collections;
import java.util.List;

public class TransactionHistoryScreen {

    private static final Integer QUERY_LIMIT = 10;

    private final TransactionService transactionService = new TransactionService();

    public void showTransactionHistories(Account userAccount) {
        System.out.println("Transaction Histories : ");
        List<Transaction> userTransactions = transactionService.getTransactionsByAccountNumber(userAccount.getAccountNumber());
        if (userTransactions.equals(Collections.emptyList())) {
            return;
        }

        if (!isLimitable(userTransactions)) {
            userTransactions.forEach(transaction -> System.out.println(formattingTransactionHistories(transaction)));
        } else {
            userTransactions.stream()
                    .skip(userTransactions.size() - QUERY_LIMIT)
                    .forEach(transaction -> System.out.println(formattingTransactionHistories(transaction)));
        }
    }

    private boolean isLimitable(List<Transaction> transactions) {
        return (long) transactions.size() > QUERY_LIMIT;
    }

    private String formattingTransactionHistories(Transaction transaction){
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction Type : ").append(transaction.getType()).append("\n");
        sb.append("Created By : ").append(transaction.getCreatedBy()).append("\n");
        sb.append("Date Time : ").append(transaction.getDateTime()).append("\n");

        if (transaction.getType().equals(Transaction.TransactionType.WITHDRAW)) {
            Transaction.Withdraw withdraw = (Transaction.Withdraw) transaction;
            sb.append("Amount : ").append(withdraw.getAmount()).append("\n");
        } else {
            Transaction.Transfer transfer = (Transaction.Transfer) transaction;
            sb.append("Origin Account Number : ").append(transfer.getOriginAccountNo()).append("\n");
            sb.append("Destination Account Number : ").append(transfer.getDestinationAccountNo()).append("\n");
            sb.append("Refrence Number : ").append(transfer.getRefrenceNumber()).append("\n");
            sb.append("Transfer Amount : ").append(transfer.getAmount()).append("\n");
        }
        sb.append("--- *** ---");
        return sb.toString();
    }
}
