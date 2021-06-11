package com.my.example.atm.dao.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.my.example.atm.dao.entity.Transaction.TransactionType.WITHDRAW;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "transaction_type")
    private TransactionType type;

    @Column(name = "date_time")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(createdBy, that.createdBy) && type == that.type && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, type, dateTime);
    }

    @Entity
    public static class Transfer extends Transaction{

        @Column(name = "destination_account_no")
        private String destinationAccountNo;

        @Column(name = "origin_account_no")
        private String originAccountNo;

        @Column(name = "amount")
        private Long amount;

        @Column(name = "refrence_number")
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Transfer transfer = (Transfer) o;
            return Objects.equals(destinationAccountNo, transfer.destinationAccountNo) && Objects.equals(originAccountNo, transfer.originAccountNo) && Objects.equals(amount, transfer.amount) && Objects.equals(refrenceNumber, transfer.refrenceNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), destinationAccountNo, originAccountNo, amount, refrenceNumber);
        }
    }

    @Entity
    public static class Withdraw extends Transaction{

        @Column(name = "amount")
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Withdraw withdraw = (Withdraw) o;
            return Objects.equals(amount, withdraw.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), amount);
        }
    }

    public enum TransactionType {
        TRANSFER, WITHDRAW
    }
}
