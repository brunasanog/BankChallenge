package dao;

import model.Account;
import db.DatabaseConnection;

import java.sql.*;

public class AccountDAO {

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



}
