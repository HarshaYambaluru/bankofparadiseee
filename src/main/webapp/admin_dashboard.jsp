<%@ page import="java.sql.*" %>
<%@ page import="bank.util.DatabaseConnection" %> <!-- Import for DatabaseConnection class -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache"); 
    response.setHeader("Expires", "0"); // prevents caching at the proxy server
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Bank of Paradise</title>
    <link rel="stylesheet" type="text/css" href="./css/admin_dashboard.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            color: #333333;
            margin: 0;
            padding: 0;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: #343a40;
            color: #ffffff;
        }
        .header-container h2 {
            margin: 0;
        }
        .button-container {
            display: flex;
            gap: 10px;
        }
        .button-container button {
            padding: 10px 20px;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }
        .logout-button {
            background-color: #dc3545;
            color: #ffffff;
        }
        .logout-button:hover {
            background-color: #c82333;
            transform: translateY(-3px);
        }
        .new-user-button {
            background-color: #007bff;
            color: #ffffff;
        }
        .new-user-button:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }
        h3 {
            margin-top: 20px;
            color: #343a40;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #dee2e6;
        }
        th {
            background-color: #343a40;
            color: #ffffff;
        }
        td {
            background-color: #ffffff;
        }
        .update-button {
            background-color: #28a745;
            color: #ffffff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }
        .update-button:hover {
            background-color: #218838;
            transform: translateY(-3px);
        }
        .delete-button {
            background-color: #dc3545;
            color: #ffffff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }
        .delete-button:hover {
            background-color: #c82333;
            transform: translateY(-3px);
        }
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #343a40;
            color: #ffffff;
            margin-top: 50px;
            position: fixed;
            width: 100%;
            bottom: 0;
        }
    </style>
</head>
<body>
    <div class="header-container">
        <h2>Welcome Admin</h2>
        <div class="button-container">
            <button class="logout-button" onclick="logout()">Logout</button>
            <a href="register.jsp"><button class="new-user-button">REGISTER A NEW USER</button></a>
        </div>
    </div>

    <h3>Customer Details</h3>
    <table>
        <tr>
            <th>Account No</th>
            <th>Full Name</th>
            <th>Address</th>
            <th>Mobile No</th>
            <th>Email ID</th>
            <th>Account Type</th>
            <th>Date of Birth</th>
            <th>ID Proof</th>
            <th>Actions</th>
        </tr>
        <%
            Connection con = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                con = DatabaseConnection.getConnection(); // Using DatabaseConnection class for database connectivity
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM customer");
                while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("account_no") %></td>
            <td><%= rs.getString("full_name") %></td>
            <td><%= rs.getString("address") %></td>
            <td><%= rs.getString("mobile_no") %></td>
            <td><%= rs.getString("email") %></td>
            <td><%= rs.getString("account_type") %></td>
            <td><%= rs.getDate("dob") %></td>
            <td><%= rs.getString("id_proof") %></td>
            <td>
                <a href="update_customer.jsp?account_no=<%= rs.getInt("account_no") %>">
                    <button class="update-button">Update</button>
                </a>
                <a href="delete_customer.jsp?account_no=<%= rs.getInt("account_no") %>" onclick="return confirm('Are you sure?')">
                    <button class="delete-button">Delete</button>
                </a>
            </td>
        </tr>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        %>
    </table>

    <div class="footer">
        &copy; 2024 All Rights Reserved by Harsha
    </div>

    <script>
        function logout() {
            window.location.href = "index.jsp";
        }

        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        }
    </script>
</body>
</html>
