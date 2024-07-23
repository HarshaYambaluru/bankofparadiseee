<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache"); 
response.setHeader ("Expires", "0"); // prevents caching at the proxy server
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Customer - Bank of Paradise</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            color: #333333;
            margin: 0;
            padding: 20px;
        }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .form-container h2 {
            text-align: center;
            color: #343a40;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
            color: #343a40;
        }
        .form-container input[type="text"],
        .form-container input[type="radio"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #dee2e6;
            border-radius: 5px;
        }
        .form-container input[type="submit"] {
            background-color: #007bff;
            color: #ffffff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
            width: 100%;
        }
        .form-container input[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #343a40;
            color: #ffffff;
            margin-top: 50px;
        }
    </style>
</head>
<body>
<%
    String account_no = request.getParameter("account_no");
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_app", "root", "20507");
    Statement stmt = con.createStatement();
    String query = "SELECT * FROM customer WHERE account_no = " + account_no;
    ResultSet rs = stmt.executeQuery(query);
    if(rs.next()) {
%>

<div class="form-container">
    <h2>Update Customer</h2>
    <form action="update_process.jsp" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="account_no" value="<%= rs.getString("account_no") %>" />

        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="full_name" value="<%= rs.getString("full_name") %>" />

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%= rs.getString("address") %>" />

        <label for="mobileNo">Mobile No:</label>
        <input type="text" id="mobileNo" name="mobile_no" value="<%= rs.getString("mobile_no") %>" />

        <label for="email">Email ID:</label>
        <input type="text" id="email" name="email" value="<%= rs.getString("email") %>" />

        <label for="accountType">Account Type:</label>
        <input type="text" id="accountType" name="account_type" value="<%= rs.getString("account_type") %>" />

        <label for="dob">Date of Birth:</label>
        <input type="text" id="dob" name="dob" value="<%= rs.getString("dob") %>" />

        <label>ID Proof:</label>
        <input type="radio" id="aadhar" name="id_proof" value="Aadhar" <%= rs.getString("id_proof").equals("Aadhar") ? "checked" : "" %> />
        <label for="aadhar">Aadhar</label>
        <input type="radio" id="pan" name="id_proof" value="PAN" <%= rs.getString("id_proof").equals("PAN") ? "checked" : "" %> />
        <label for="pan">PAN</label>

        <input type="submit" value="Update Customer" />
    </form>
</div>

<%
    }
    con.close();
%>

<div class="footer">
    &copy; 2024 All Rights Reserved by Harsha
</div>

<script>
    function validateForm() {
        var fullName = document.getElementById("fullName").value;
        var address = document.getElementById("address").value;
        var mobileNo = document.getElementById("mobileNo").value;
        var email = document.getElementById("email").value;
        var dob = document.getElementById("dob").value;

        // Additional validation for ID Proof radio buttons
        var aadharChecked = document.getElementById("aadhar").checked;
        var panChecked = document.getElementById("pan").checked;
        if (!aadharChecked && !panChecked) {
            alert("Please select an ID Proof option.");
            return false;
        }

        // Basic validation for empty fields
        if (fullName === "" || address === "" || mobileNo === "" || email === "" || dob === "") {
            alert("All fields are required!");
            return false;
        }

        return true;
    }

    // Disable back button
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
</script>

</body>
</html>
