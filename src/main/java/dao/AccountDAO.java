package dao;

import model.Account;
import db.DatabaseConnection;

import java.sql.*;

public class AccountDAO {

    public void createAccount(Account account) {
        String sql = "INSERT INTO account (user_id, balance, account_type) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, account.getUserId());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getAccountType());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while creating account: " + e.getMessage());
        }
    }

}
