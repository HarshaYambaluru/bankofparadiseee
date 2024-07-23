package bank.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import bank.dao.ChangePasswordDAO;
import bank.model.Customer;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private ChangePasswordDAO changePasswordDAO;

    @Override
    public void init() throws ServletException {
        changePasswordDAO = new ChangePasswordDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNo = (String) session.getAttribute("accountNo");
        
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        try {
            String storedPassword = changePasswordDAO.getStoredPassword(accountNo);
            if (storedPassword == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            
            if (!currentPassword.equals(storedPassword)) {
                response.sendRedirect("change_password.jsp?error=incorrect");
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("change_password.jsp?error=mismatch");
                return;
            }
            
            Customer customer = new Customer();
            customer.setAccountNo(accountNo);
            customer.setPassword(newPassword);
            
            boolean updateSuccess = changePasswordDAO.updatePassword(customer);
            
            if (updateSuccess) {
                response.sendRedirect("customerlogin.jsp");
            } else {
                response.sendRedirect("change_password.jsp?error=update_failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}