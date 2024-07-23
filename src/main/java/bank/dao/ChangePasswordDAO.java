package bank.dao;

import bank.model.Customer;
import bank.util.DatabaseConnection;
import java.sql.*;

public class ChangePasswordDAO {
    private static final String GET_PASSWORD_QUERY = "SELECT password FROM Customer WHERE account_no = ?";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE Customer SET password = ? WHERE account_no = ?";

    public String getStoredPassword(String accountNo) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_PASSWORD_QUERY)) {
            pstmt.setString(1, accountNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        }
        return null;
    }

    public boolean updatePassword(Customer customer) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_PASSWORD_QUERY)) {
            pstmt.setString(1, customer.getPassword());
            pstmt.setString(2, customer.getAccountNo());
      
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}