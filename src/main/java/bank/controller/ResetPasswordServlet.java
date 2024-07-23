package bank.controller;
//ResetPasswordServlet.java


import bank.dao.ResetPasswordDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
 // Load the JDBC driver when the servlet is initialized
 @Override
 public void init() throws ServletException {
     try {
         Class.forName("com.mysql.cj.jdbc.Driver");
     } catch (ClassNotFoundException e) {
         e.printStackTrace();
     }
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     // Get parameters from the request
     String newPassword = request.getParameter("newPassword");
     String accountNo = request.getSession().getAttribute("accountNo").toString();

     ResetPasswordDAO resetPasswordDAO = new ResetPasswordDAO();

     try {
         boolean resetSuccessful = resetPasswordDAO.resetPassword(accountNo, newPassword);

         if (resetSuccessful) {
             // Password reset successful
             response.setStatus(HttpServletResponse.SC_OK);
         } else {
             // Password reset failed
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to reset password.");
         }
     } catch (SQLException e) {
         e.printStackTrace(); // Log the exception
         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request.");
     }
 }
}