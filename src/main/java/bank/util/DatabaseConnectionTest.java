package bank.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
                conn.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
