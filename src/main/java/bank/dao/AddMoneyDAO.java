package bank.dao;

import bank.model.Transaction;
import bank.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;

public class AddMoneyDAO {
    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE customer SET initial_balance = initial_balance + ? WHERE account_no = ?";
    private static final String INSERT_TRANSACTION_QUERY = "INSERT INTO transaction (account_no, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";

    public void addMoney(Transaction transaction) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            updateStatement = connection.prepareStatement(UPDATE_CUSTOMER_QUERY);
            updateStatement.setDouble(1, transaction.getAmount());
            updateStatement.setString(2, transaction.getAccountNo());
            updateStatement.executeUpdate();

            insertStatement = connection.prepareStatement(INSERT_TRANSACTION_QUERY);
            insertStatement.setString(1, transaction.getAccountNo());
            insertStatement.setString(2, transaction.getTransactionType());
            insertStatement.setDouble(3, transaction.getAmount());
            insertStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            insertStatement.executeUpdate();

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