package bank.controller;


import bank.dao.TransactionDAO;
import bank.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/GetTransactionDetailsServlet")
public class GetTransactionDetailsServlet extends HttpServlet {
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String accountNo = request.getSession().getAttribute("accountNo").toString();
     
     try {
         TransactionDAO transactionDAO = new TransactionDAO();
         List<Transaction> transactions = transactionDAO.getTransactionsByAccountNo(accountNo);
         
         String json = convertTransactionsToJson(transactions);
         
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");
         response.getWriter().write(json);
         
     } catch (SQLException e) {
         e.printStackTrace();
         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while fetching transaction details.");
     }
 }

 private String convertTransactionsToJson(List<Transaction> transactions) {
     StringBuilder json = new StringBuilder("[");
     for (Transaction transaction : transactions) {
         json.append(transaction.toJson()).append(",");
     }
     if (!transactions.isEmpty()) {
         json.deleteCharAt(json.length() - 1); // Remove trailing comma
     }
     json.append("]");
     return json.toString();
 }
}