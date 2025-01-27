package dao;

import db.DatabaseConnection;

import java.sql.*;

public abstract class BaseDAO {

    //QUERY
    protected <T> T executeQuery(String sql, QueryExecutor<T> executor) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            return executor.execute(stmt);
        } catch (SQLException e) {
            System.out.println("Error while executing query: " + e.getMessage());
            return null;
        }
    }

    //UPDATE
    protected void executeUpdate(String sql, PreparedStatementSetter setter) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            setter.setValues(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while executing update: " + e.getMessage());
        }
    }

    @FunctionalInterface
    interface QueryExecutor<T> {
        T execute(PreparedStatement stmt) throws SQLException;
    }

    @FunctionalInterface
    interface PreparedStatementSetter {
        void setValues(PreparedStatement stmt) throws SQLException;
    }
}
