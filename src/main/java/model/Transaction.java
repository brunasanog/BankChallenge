package model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int accountId;
    private String transactionType; // "WITHDRAWAL", "DEPOSIT", "TRANSFER"
    private double amount;
    private LocalDateTime transactionDate; // Usar LocalDateTime para armazenar data e hora

    public Transaction(int accountId, String transactionType, double amount) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now(); // Define a data e hora atual
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

}