package dao;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends BaseDAO {

    // CREATE ACCOUNT
    public void createAccount(Account account) {
        String sql = "INSERT INTO account (user_id, balance, account_type) VALUES (?, ?, ?)";

        int generatedId = executeUpdateWithGeneratedKeys(sql, stmt -> {
            stmt.setInt(1, account.getUserId());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getAccountType());
        });

        if (generatedId > 0) {
            account.setId(generatedId);
            System.out.println("\nAccount created with ID: " + account.getId());
        } else {
            System.out.println("Failed to create account.");
        }
    }

    // GET ACCOUNT BY USER ID
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";
        return executeQuery(sql, stmt -> {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
                return account;
            }
            return null;
        });
    }

    // GET ACCOUNT BY ID
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM account WHERE id = ?";
        return executeQuery(sql, stmt -> {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
                return account;
            }
            return null;
        });
    }

    // UPDATE BALANCE
    public void updateAccount(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        executeUpdate(sql, stmt -> {
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getId());
        });
    }

    // GET ALL ACCOUNTS FROM USER
    public List<Account> getAccountsByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";
        return executeQuery(sql, stmt -> {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
                accounts.add(account);
            }
            return accounts;
        });
    }
}