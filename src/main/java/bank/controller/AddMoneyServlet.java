package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.dao.AddMoneyDAO;
import bank.model.Transaction;

@WebServlet("/AddMoneyServlet")
public class AddMoneyServlet extends HttpServlet {
    private AddMoneyDAO addMoneyDAO;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            addMoneyDAO = new AddMoneyDAO();
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to load JDBC driver", e);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amountToAdd = request.getParameter("amountToAdd");
        String accountNo = request.getSession().getAttribute("accountNo").toString();

        try {
            double amount = Double.parseDouble(amountToAdd);
            Transaction transaction = new Transaction(accountNo, "Deposit", amount);
            addMoneyDAO.addMoney(transaction);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing the request.");
        }
    }
}