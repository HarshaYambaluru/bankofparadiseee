// ResetPasswordDAO.java
package bank.dao;

import bank.util.DatabaseConnection;

import java.sql.*;

public class ResetPasswordDAO {
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE customer SET password = ? WHERE account_no = ?";

    public boolean resetPassword(String accountNo, String newPassword) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_PASSWORD_QUERY)) {

            updateStatement.setString(1, newPassword);
            updateStatement.setString(2, accountNo);
            int rowsAffected = updateStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }
}