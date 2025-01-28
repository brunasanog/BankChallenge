package dao;

import model.User;

import java.sql.*;

public class UserDAO extends BaseDAO {

    // CREATE USER
    public int createUser(User user) {
        String sql = "INSERT INTO user (cpf, name, email, phone, birth_date, account_type, password) VALUES (?,?,?,?,?,?,?)";

        return executeUpdateWithGeneratedKeys(sql, stmt -> {
            stmt.setString(1, user.getCpf());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setDate(5, java.sql.Date.valueOf(user.getBirthDate()));
            stmt.setString(6, user.getAccountType());
            stmt.setString(7, user.getPassword());
        });
    }

    // CHECK CPF
    public boolean isCpfRegistered(String cpf) {
        String sql = "SELECT COUNT(*) FROM user WHERE cpf = ?";

        return Boolean.TRUE.equals(executeQuery(sql, stmt -> {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            return false;
        }));
    }

    // FIND USER BY CPF
    public User findByCpf(String cpf) {
        String sql = "SELECT * FROM user WHERE cpf = ?";

        return executeQuery(sql, stmt -> {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("cpf"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("account_type"),
                        rs.getString("password")
                );
                user.setId(rs.getInt("id"));
                return user;
            }
            return null;
        });
    }
}
