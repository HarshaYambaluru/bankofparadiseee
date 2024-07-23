<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.*, java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="bank.util.DatabaseConnection" %>

<%
    String adminUsername = request.getParameter("adminUsername");
    String adminPassword = request.getParameter("adminPassword");

    boolean adminLoggedIn = false;

    try {
        // Establish database connection using DatabaseConnection class
        Connection conn = DatabaseConnection.getConnection();
        // Check admin credentials
        String query = "SELECT * FROM admin WHERE username=? AND password=?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, adminUsername);
        pstmt.setString(2, adminPassword);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            adminLoggedIn = true;
            response.sendRedirect("admin_dashboard.jsp");
        }

        // Close database connection
        rs.close();
        pstmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (!adminLoggedIn) {
        // Admin login failed, display error message
        out.println("<h2>Admin Login Failed!</h2>");
        return;
    }
%>
