// RemoveMoneyServlet.java
package bank.controller;

import bank.dao.RemoveMoneyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RemoveMoneyServlet")
public class RemoveMoneyServlet extends HttpServlet {
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
        String amountToRemove = request.getParameter("amountToRemove");
        String accountNo = request.getSession().getAttribute("accountNo").toString();

        RemoveMoneyDAO removeMoneyDAO = new RemoveMoneyDAO();

        try {
            double amount = Double.parseDouble(amountToRemove);
            removeMoneyDAO.removeMoneyAndRecordTransaction(accountNo, amount);
            
            // Send a success response back to the client
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace(); // Log the exception
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request.");
        }
    }
}