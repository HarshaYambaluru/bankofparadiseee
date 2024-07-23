package bank.controller;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bank.dao.CustomerDAO;
import bank.model.Customer;

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        if (accountNo == null || accountNo.isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Account number is required.");
            return;
        }

        invalidateSession(request);

        try {
            Customer customer = customerDAO.getCustomerByAccountNo(accountNo);
            if (customer == null) {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Account not found.");
                return;
            }

            if (customer.getInitialBalance() != 0) {
                sendErrorResponse(response, HttpServletResponse.SC_CONFLICT, "Cannot delete account. Account balance is not zero.");
                return;
            }

            boolean deleted = customerDAO.deleteCustomerAccount(accountNo);
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request.");
        }
    }

    private void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.getWriter().println(message);
    }
}
