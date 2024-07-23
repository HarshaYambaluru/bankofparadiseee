package bank.model;

import java.sql.Date;

public class Customer {
    private String accountNo;
    private String password;
    private double initialBalance;
    private String fullName;
    private String address;
    private String mobileNo;
    private String email;
    private String accountType;
    private Date dob;
    private String idProof;

    // Default constructor
    public Customer() {
    }

    // Full constructor
    public Customer(String accountNo, String password, double initialBalance, String fullName,
                    String address, String mobileNo, String email, String accountType,
                    Date dob, String idProof) {
        this.accountNo = accountNo;
        this.password = password;
        this.initialBalance = initialBalance;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.email = email;
        this.accountType = accountType;
        this.dob = dob;
        this.idProof = idProof;
    }

    // Constructor with only account number, password, and initial balance
    public Customer(String accountNo, String password, double initialBalance) {
        this.accountNo = accountNo;
        this.password = password;
        this.initialBalance = initialBalance;
    }

    // Getters and setters
    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String i) { this.accountNo = i; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getIdProof() { return idProof; }
    public void setIdProof(String idProof) { this.idProof = idProof; }

	public void setAccountType(int accountNumber) {
		// TODO Auto-generated method stub
		
	}
}