package model;

import java.time.LocalDate;

public class Account {
    private int id;
    private int userId;
    private double balance;
    private String accountType;
    public String authorizedSource;

    public Account() {
    }

    public Account(int UserId, double balance, String accountType, String authorizedSource) {
        this.userId = UserId;
        this.balance = balance;
        this.accountType = accountType;
        this.authorizedSource = authorizedSource;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int UserId) {
        this.userId = UserId;
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
}
