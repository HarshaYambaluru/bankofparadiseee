package bank.controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank.dao.CustomerDAO;
import bank.model.Customer;
import bank.model.Transaction;
import java.util.List;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "changePassword":
                    changePassword(request, response);
                    break;
                case "viewAccountDetails":
                    viewAccountDetails(request, response);
                    break;
                case "viewTransactions":
                    viewTransactions(request, response);
                    break;
                case "deposit":
                    deposit(request, response);
                    break;
                case "withdraw":
                    withdraw(request, response);
                    break;
                case "closeAccount":
                    closeAccount(request, response);
                    break;
                default:
                    response.sendRedirect("customer_login.jsp");
            }
        } else {
            response.sendRedirect("customer_login.jsp");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        try {
            if (customerDAO.authenticateCustomer(accountNumber, password)) {
                response.sendRedirect("customer_dashboard.jsp");
            } else {
                request.setAttribute("error", "Invalid account number or password");
                request.getRequestDispatcher("customer_login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e, "Login failed");
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String newPassword = request.getParameter("newPassword");

        try {
            customerDAO.changePassword(accountNumber, newPassword);
            response.sendRedirect("customer_dashboard.jsp");
        } catch (Exception e) {
            handleException(request, response, e, "Failed to change password");
        }
    }

    private void viewAccountDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        try {
            Customer customer = customerDAO.getAccountDetails(accountNumber);
            if (customer != null) {
                request.setAttribute("accountDetails", customer);
                request.getRequestDispatcher("account_details.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Account not found");
                request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e, "Failed to retrieve account details");
        }
    }

    private void viewTransactions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        try {
            List<Transaction> transactions = customerDAO.getLastTenTransactions(accountNumber);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("transactions.jsp").forward(request, response);
        } catch (Exception e) {
            handleException(request, response, e, "Failed to retrieve transactions");
        }
    }

    private void deposit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            customerDAO.deposit(accountNumber, amount);
            response.sendRedirect("customer_dashboard.jsp");
        } catch (Exception e) {
            handleException(request, response, e, "Failed to deposit money");
        }
    }

    private void withdraw(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            if (customerDAO.withdraw(accountNumber, amount)) {
                response.sendRedirect("customer_dashboard.jsp");
            } else {
                request.setAttribute("error", "Insufficient balance");
                request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e, "Failed to withdraw money");
        }
    }

    private void closeAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        try {
            if (customerDAO.closeAccount(accountNumber)) {
                response.sendRedirect("customer_login.jsp");
            } else {
                request.setAttribute("error", "Account balance must be 0 to close the account");
                request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleException(request, response, e, "Failed to close account");
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e, String errorMessage)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("customer_dashboard.jsp").forward(request, response);
    }
}
