package dao;

import db.DatabaseConnection;
import model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    //CREATE TRANSACTION
    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (transaction_type, amount, transaction_date, account_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, transaction.getTransactionType());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setInt(4, transaction.getAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while creating transaction: " + e.getMessage());
        }
    }

    // RETURN TRANSACTION
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC"; // Corrigido para usar transaction_date

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount")
                );
                transaction.setId(rs.getInt("id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime()); // Adicione esta linha para definir a data da transação
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }
}