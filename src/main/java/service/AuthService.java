package service;

import model.User;
import util.PasswordUtil;
import db.DatabaseConnection;
import model.AccountType;

import java.sql.*;

public class AuthService {

    public String isCpfRegistered(String cpf) {
        String sql = "SELECT COUNT(*) FROM user WHERE cpf = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return "CPF already registered! Please use a different CPF or login.";
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check CPF: " + e.getMessage());
            return "An error occurred while checking the CPF.";
        }
        return null;
    }

    public String isValidAccountType(String accountTypeInput) {
        try {
            AccountType.valueOf(accountTypeInput.toUpperCase());
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid account type! Please choose CHECKING, SAVINGS, or SALARY.";
        }
    }
}
