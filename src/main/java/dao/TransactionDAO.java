package dao;

import model.Transaction;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseDAO {

    // CREATE TRANSACTION
    public int createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (transaction_type, amount, transaction_date, account_id) VALUES (?, ?, ?, ?)";

        int generatedId = executeUpdateWithGeneratedKeys(sql, stmt -> {
            stmt.setString(1, transaction.getTransactionType());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setInt(4, transaction.getAccountId());
        });

        if (generatedId > 0) {
            transaction.setId(generatedId);
            System.out.println("\nTransaction created with ID: " + transaction.getId());
        } else {
            System.out.println("Failed to create transaction.");
        }

        return generatedId;
    }

    // GET FORMATTED TRANSACTIONS
    public List<String> getFormattedTransactionsByAccountId(int accountId) {
        String sql = "SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC";
        return executeQuery(sql, stmt -> {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            List<String> formattedTransactions = new ArrayList<>();

            while (rs.next()) {
                String formattedTransaction = String.format("ID: %d | Tipo: %s | Valor: R$%.2f | Data: %s",
                        rs.getInt("id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

                formattedTransactions.add(formattedTransaction);
            }
            return formattedTransactions;
        });
    }
}