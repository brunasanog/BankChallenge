package service;

import db.DatabaseConnection;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserService {

    private final AuthService authService = new AuthService();

    public void createUser(String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {

        // Validação de CPF
        String cpfError = authService.isCpfRegistered(cpf);
        if (cpfError != null) {
            System.out.println(cpfError);
            return;
        }

        // Validação de tipo de conta
        String accountTypeError = authService.isValidAccountType(accountType);
        if (accountTypeError != null) {
            System.out.println(accountTypeError);
            return;
        }

        //Validação do formato da data
        if (birthDate == null) {
            System.out.println("Data de nascimento inválida.");
            return;
        }

        // SQL de inserção
        String sql = "INSERT INTO user (cpf, name, email, phone, birth_date, account_type, password) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setDate(5, java.sql.Date.valueOf(birthDate));
            stmt.setString(6, accountType);
            stmt.setString(7, PasswordUtil.hashPassword(password));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User successfully created!");
            } else {
                System.out.println("Failed to create user.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to create user: " + e.getMessage());
        }
    }

}
