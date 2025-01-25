package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {

    //connection
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    //get the connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Failed to establish a database connection. Please check your database URL, username, or password. Error details: " + e.getMessage());
        }
    }

    //test the connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connection established!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to establish a database connection. Please check your database URL, username, or password. Error details: " + e.getMessage());
        }
    }
}
