package com.my.example.atm.dao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.my.example.atm.dao.entity.Transaction.TransactionType.WITHDRAW;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String createdBy;
    private TransactionType type;
    private LocalDateTime dateTime;

    public Transaction(String createdBy, TransactionType type, LocalDateTime dateTime) {
        this.createdBy = createdBy;
        this.type = type;
        this.dateTime = dateTime;
    }

    public Transaction() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Entity
    public static class Transfer extends Transaction{

        private String destinationAccountNo;

        private String originAccountNo;

        private Long amount;

        private String refrenceNumber;

        public Transfer(){ }

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

    @Entity
    public static class Withdraw extends Transaction{

        private Long amount;

        public Withdraw(){ }

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
