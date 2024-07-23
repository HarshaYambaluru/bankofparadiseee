package bank.dao;

import bank.model.Customer;
import bank.util.DatabaseConnection;
import java.sql.*;

public class CustomerLoginDAO {
    private static final String LOGIN_QUERY = "SELECT * FROM customer WHERE account_no=? AND password=?";

    public Customer validateCustomer(String accountNo, String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(LOGIN_QUERY)) {
            
            pstmt.setString(1, accountNo);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double initialBalance = rs.getDouble("initial_balance");
                    return new Customer(accountNo, password, initialBalance);
                }
            }
        }
        return null;
    }
}