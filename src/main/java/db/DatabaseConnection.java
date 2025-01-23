package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    //connection
    private static final String URL = "jdbc:mysql://localhost:3306/bank_challenge";
    private static final String USER = "root";
    private static final String PASSWORD = "1254";

    //get the connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //test the connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connection established!");
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
