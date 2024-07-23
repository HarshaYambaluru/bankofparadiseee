// RemoveMoneyDAO.java
package bank.dao;

import bank.model.Transaction;
import bank.model.Customer;
import bank.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class RemoveMoneyDAO {
    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE customer SET initial_balance = initial_balance - ? WHERE account_no = ?";
    private static final String INSERT_TRANSACTION_QUERY = "INSERT INTO transaction (account_no, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";

    public void removeMoneyAndRecordTransaction(String accountNo, double amount) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Update balance in the customer table
            updateStatement = connection.prepareStatement(UPDATE_CUSTOMER_QUERY);
            updateStatement.setDouble(1, amount);
            updateStatement.setString(2, accountNo);
            updateStatement.executeUpdate();

            // Insert a new transaction record
            insertStatement = connection.prepareStatement(INSERT_TRANSACTION_QUERY);
            insertStatement.setString(1, accountNo);
            insertStatement.setString(2, "Withdrawal");
            insertStatement.setDouble(3, amount);
            insertStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            insertStatement.executeUpdate();

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (updateStatement != null) updateStatement.close();
            if (insertStatement != null) insertStatement.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}