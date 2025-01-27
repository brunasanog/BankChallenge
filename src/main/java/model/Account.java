package model;

public class Account {
    private int id;
    private int userId;
    private double balance;
    private String accountType;

    public Account() {
    }

    public Account(int UserId, double balance, String accountType) {
        this.userId = UserId;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
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

}
