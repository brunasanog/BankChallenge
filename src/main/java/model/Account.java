package model;

import java.time.LocalDate;

public class Account {
    private int id;
    private int UserId;
    private double balance;
    private String accountType;
    public String authorizedSource;
    private LocalDate createdAt;

    public Account() {
    }

    public Account(int id, int UserId, double balance, String accountType, String authorizedSource, LocalDate createdAt) {
        this.id = id;
        this.UserId = UserId;
        this.balance = balance;
        this.accountType = accountType;
        this.authorizedSource = authorizedSource;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAuthorizedSource() {
        return authorizedSource;
    }

    public void setAuthorizedSource(String authorizedSource) {
        this.authorizedSource = authorizedSource;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
