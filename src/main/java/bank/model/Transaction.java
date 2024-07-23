package bank.model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private String accountNo;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;

    // Constructor with all parameters
    public Transaction(int id, String accountNo, String transactionType, double amount, Timestamp transactionDate) {
        this.id = id;
        this.accountNo = accountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    // Constructor with accountNo, transactionType, and amount
    public Transaction(String accountNo, String transactionType, double amount) {
        this.accountNo = accountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = new Timestamp(System.currentTimeMillis());
    }

    // Default constructor
    public Transaction() {
        // TODO Auto-generated constructor stub
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }

    // JSON conversion method
    public String toJson() {
        return "{"
                + "\"id\": " + this.id + ","
                + "\"accountNo\": \"" + this.accountNo + "\","
                + "\"transactionType\": \"" + this.transactionType + "\","
                + "\"amount\": " + this.amount + ","
                + "\"transactionDate\": \"" + this.transactionDate + "\""
                + "}";
    }
}
