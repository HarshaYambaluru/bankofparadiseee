<%@ page import="java.sql.*" %>
<%@ page import="bank.util.DatabaseConnection" %>
<%
    try {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            out.println("<h2>Database connection successful!</h2>");
            conn.close();
        } else {
            out.println("<h2>Failed to connect to the database.</h2>");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        out.println("<h2>Error: " + e.getMessage() + "</h2>");
    }
%>
