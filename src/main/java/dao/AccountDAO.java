package dao;

import model.Account;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    //CREATE ACCOUNT
    public void createAccount(Account account) {
        String sql = "INSERT INTO account (user_id, balance, account_type) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, account.getUserId());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getAccountType());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                    System.out.println("Account created with ID: " + account.getId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while creating account: " + e.getMessage());
        }
    }

    //GET ACCOUNT BY USER ID
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";
        Account account = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving account: " + e.getMessage());
        }
        return account;
    }

    //GET ACCOUNT BY ID
    public Account getAccountById(int accountId) {
        String sql = "SELECT * FROM account WHERE id = ?";
        Account account = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving account: " + e.getMessage());
        }
        return account;
    }

    //UPDATE BALANCE
    public void updateAccount(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating account: " + e.getMessage());
        }
    }

    //GET ALL ACCOUNTS FROM USER
    public List<Account> getAccountsByUserId(int userId) {
        String sql = "SELECT * FROM account WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                account.setId(rs.getInt("id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving accounts: " + e.getMessage());
        }
        return accounts;
    }


}
