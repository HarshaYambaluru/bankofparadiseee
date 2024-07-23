package bank.dao;

import bank.model.Customer;
import bank.util.DatabaseConnection;
import java.sql.*;

public class CustomerRegistrationDAO {
    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customer (full_name, address, mobile_no, email, account_type, initial_balance, dob, id_proof, account_no, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_TRANSACTION_QUERY = "INSERT INTO transaction (account_no, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";

    public boolean registerCustomer(Customer customer) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getMobileNo());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getAccountType());
            pstmt.setDouble(6, customer.getInitialBalance());
            pstmt.setDate(7, customer.getDob());
            pstmt.setString(8, customer.getIdProof());
            pstmt.setString(9, customer.getAccountNo());
            pstmt.setString(10, customer.getPassword());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                insertInitialTransaction(conn, customer.getAccountNo(), customer.getInitialBalance());
                return true;
            }
        }
        return false;
    }

    private void insertInitialTransaction(Connection conn, String accountNo, double initialBalance) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_TRANSACTION_QUERY)) {
            pstmt.setString(1, accountNo);
            pstmt.setString(2, "Deposit");
            pstmt.setDouble(3, initialBalance);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        }
    }
}
