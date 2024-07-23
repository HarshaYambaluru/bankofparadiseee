package bank.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import bank.model.Customer;
import bank.dao.CustomerRegistrationDAO;
import java.sql.Date;

@WebServlet("/CustomerRegistrationServlet")
public class CustomerRegistrationServlet extends HttpServlet {
    private CustomerRegistrationDAO customerRegistrationDAO;

    @Override
    public void init() throws ServletException {
        customerRegistrationDAO = new CustomerRegistrationDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();
        customer.setFullName(request.getParameter("fullName"));
        customer.setAddress(request.getParameter("address"));
        customer.setMobileNo(request.getParameter("mobileNo"));
        customer.setEmail(request.getParameter("email"));
        customer.setAccountType(request.getParameter("accountType"));
        customer.setInitialBalance(Double.parseDouble(request.getParameter("initialBalance")));
        customer.setDob(Date.valueOf(request.getParameter("dob")));
        customer.setIdProof(request.getParameter("idProof"));
        customer.setAccountNo(generateAccountNumber());
        customer.setPassword(generateTemporaryPassword());

        try {
            boolean registrationSuccess = customerRegistrationDAO.registerCustomer(customer);
            if (registrationSuccess) {
                HttpSession session = request.getSession();
                session.setAttribute("account_no", customer.getAccountNo());
                response.sendRedirect("registration_success.jsp?accountNo=" + customer.getAccountNo() + "&password=" + customer.getPassword());
            } else {
                response.sendRedirect("registration_error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    
    private String generateAccountNumber() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
    
    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
