package bank.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import bank.dao.CustomerLoginDAO;
import bank.model.Customer;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private CustomerLoginDAO customerLoginDAO;

    @Override
    public void init() throws ServletException {
        customerLoginDAO = new CustomerLoginDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String password = request.getParameter("password");
        
        try {
            Customer customer = customerLoginDAO.validateCustomer(accountNo, password);
            
            if (customer != null) {
                // Customer login successful
                HttpSession session = request.getSession();
                int timeoutInSeconds = 1800; // 30 minutes
                session.setMaxInactiveInterval(timeoutInSeconds);
                session.setAttribute("accountNo", customer.getAccountNo());
                session.setAttribute("initialBalance", customer.getInitialBalance());
                
                response.sendRedirect("customer_dashboard.jsp");
            } else {
                // Customer login failed
                response.sendRedirect("customer_login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}