package com.my.example.atm.entity;

import java.time.LocalDateTime;

import static com.my.example.atm.entity.Transaction.TransactionType.WITHDRAW;

public abstract class Transaction {

    private String createdBy;

    private TransactionType type;

    private LocalDateTime dateTime;

    public Transaction(String createdBy, TransactionType type, LocalDateTime dateTime) {
        this.createdBy = createdBy;
        this.type = type;
        this.dateTime = dateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "createdBy='" + createdBy + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                '}';
    }


    public static class Transfer extends Transaction{

        private String destinationAccountNo;

        private String originAccountNo;

        private Long amount;

        private String refrenceNumber;

        public Transfer(String createdBy, LocalDateTime dateTime,
                        String destinationAccountNo, String originAccountNo, Long amount, String refrenceNumber) {
            super(createdBy, Transaction.TransactionType.TRANSFER, dateTime);
            this.destinationAccountNo = destinationAccountNo;
            this.originAccountNo = originAccountNo;
            this.amount = amount;
            this.refrenceNumber = refrenceNumber;
        }

        public String getDestinationAccountNo() {
            return destinationAccountNo;
        }

        public void setDestinationAccountNo(String destinationAccountNo) {
            this.destinationAccountNo = destinationAccountNo;
        }

        public String getOriginAccountNo() {
            return originAccountNo;
        }

        public void setOriginAccountNo(String originAccountNo) {
            this.originAccountNo = originAccountNo;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getRefrenceNumber() {
            return refrenceNumber;
        }

        public void setRefrenceNumber(String refrenceNumber) {
            this.refrenceNumber = refrenceNumber;
        }

        @Override
        public String toString() {
            return "Transfer{" +
                    "destinationAccountNo='" + destinationAccountNo + '\'' +
                    ", originAccountNo='" + originAccountNo + '\'' +
                    ", amount=" + amount +
                    ", refrenceNumber=" + refrenceNumber +
                    '}';
        }
    }

    public static class Withdraw extends Transaction{

        private Long amount;

        public Withdraw(String createdBy, LocalDateTime dateTime, Long amount) {
            super(createdBy, WITHDRAW, dateTime);
            this.amount = amount;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Withdraw{" +
                    "amount=" + amount +
                    '}';
        }
    }

    public enum TransactionType {
        TRANSFER, WITHDRAW
    }
}
