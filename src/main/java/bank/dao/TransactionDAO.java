
package bank.dao;

import bank.model.Transaction;
import bank.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
 private static final String SELECT_TRANSACTION_QUERY = "SELECT * FROM transaction WHERE account_no = ?";

 public List<Transaction> getTransactionsByAccountNo(String accountNo) throws SQLException {
     List<Transaction> transactions = new ArrayList<>();

     try (Connection connection = DatabaseConnection.getConnection();
          PreparedStatement selectStatement = connection.prepareStatement(SELECT_TRANSACTION_QUERY)) {

         selectStatement.setString(1, accountNo);
         try (ResultSet resultSet = selectStatement.executeQuery()) {
             while (resultSet.next()) {
                 Transaction transaction = new Transaction();
                 transaction.setId(resultSet.getInt("id"));
                 transaction.setAccountNo(resultSet.getString("account_no"));
                 transaction.setTransactionType(resultSet.getString("transaction_type"));
                 transaction.setAmount(resultSet.getDouble("amount"));
                 transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
                 transactions.add(transaction);
             }
         }
     }
     return transactions;
 }
}