package bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bank.model.Customer;
import bank.model.Transaction;
import bank.util.DatabaseConnection;

public class CustomerDAO {
    public boolean authenticateCustomer(String accountNumber, String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customer WHERE AccountNumber=? AND TemporaryPassword=?")) {
            ps.setString(1, accountNumber);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void changePassword(String accountNumber, String newPassword) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Customer SET TemporaryPassword=? WHERE AccountNumber=?")) {
            ps.setString(1, newPassword);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        }
    }

    public Customer getAccountDetails(String accountNumber) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Account WHERE AccountNumber=?")) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setAccountNo(rs.getString("AccountNumber"));
                    customer.setInitialBalance(rs.getDouble("Balance"));
                    // Set other properties as needed
                    return customer;
                }
            }
        }
        return null;
    }

    public Customer getCustomerByAccountNo(String accountNo) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT initial_balance FROM customer WHERE account_no = ?")) {
            stmt.setString(1, accountNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setAccountNo(accountNo);
                    customer.setInitialBalance(rs.getDouble("initial_balance"));
                    return customer;
                }
            }
        }
        return null;
    }

    public List<Transaction> getLastTenTransactions(String accountNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Transaction WHERE AccountNumber=? ORDER BY TransactionDate DESC LIMIT 10")) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setAccountNo(rs.getString("AccountNumber"));
                    transaction.setTransactionType(rs.getString("TransactionType"));
                    transaction.setAmount(rs.getDouble("Amount"));
                    transaction.setTransactionDate(rs.getTimestamp("TransactionDate"));
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public void deposit(String accountNumber, double amount) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update balance
            try (PreparedStatement ps = conn.prepareStatement("UPDATE Account SET Balance = Balance + ? WHERE AccountNumber = ?")) {
                ps.setDouble(1, amount);
                ps.setString(2, accountNumber);
                ps.executeUpdate();
            }

            // Record transaction
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Transaction (AccountNumber, TransactionType, Amount) VALUES (?, ?, ?)")) {
                ps.setString(1, accountNumber);
                ps.setString(2, "Deposit");
                ps.setDouble(3, amount);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean withdraw(String accountNumber, double amount) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Check balance
            double currentBalance;
            try (PreparedStatement ps = conn.prepareStatement("SELECT Balance FROM Account WHERE AccountNumber = ?")) {
                ps.setString(1, accountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    currentBalance = rs.getDouble("Balance");
                }
            }

            if (currentBalance < amount) {
                return false;
            }

            // Update balance
            try (PreparedStatement ps = conn.prepareStatement("UPDATE Account SET Balance = Balance - ? WHERE AccountNumber = ?")) {
                ps.setDouble(1, amount);
                ps.setString(2, accountNumber);
                ps.executeUpdate();
            }

            // Record transaction
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Transaction (AccountNumber, TransactionType, Amount) VALUES (?, ?, ?)")) {
                ps.setString(1, accountNumber);
                ps.setString(2, "Withdrawal");
                ps.setDouble(3, amount);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean closeAccount(String accountNumber) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Check balance
            double balance;
            try (PreparedStatement ps = conn.prepareStatement("SELECT Balance FROM Account WHERE AccountNumber = ?")) {
                ps.setString(1, accountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    balance = rs.getDouble("Balance");
                }
            }

            if (balance != 0) {
                return false;
            }

            // Close account
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Customer WHERE AccountNumber = ?")) {
                ps.setString(1, accountNumber);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean deleteCustomerAccount(String accountNo) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM customer WHERE account_no = ?")) {
            stmt.setString(1, accountNo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}