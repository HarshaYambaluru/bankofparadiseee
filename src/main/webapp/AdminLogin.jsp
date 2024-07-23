<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache"); 
response.setHeader("Expires", "0"); // prevents caching at the proxy server
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login - Bank of Paradise</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #ffffff;
            color: #333333;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        .container {
            margin-top: 50px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .header {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px 40px;
            background-color: #f8f9fa;
            border-bottom: 2px solid #e9ecef;
        }
        .header img {
            height: 60px;
            margin-right: 20px;
        }
        .header h2 {
            font-size: 32px;
            color: #333333;
        }
        .login-form {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 30px;
            border: 1px solid #e9ecef;
            background-color: #f8f9fa;
            border-radius: 10px;
            width: 100%;
            max-width: 400px;
            margin-top: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }
        .login-form:hover {
            transform: translateY(-5px);
        }
        .login-form h2 {
            margin-bottom: 20px;
            color: #333333;
        }
        .login-form label {
            display: block;
            font-size: 18px;
            margin-bottom: 5px;
        }
        .login-form input[type="text"], .login-form input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #e9ecef;
            border-radius: 5px;
            font-size: 16px;
        }
        .login-form input[type="submit"] {
            background-color: #007bff;
            color: #ffffff;
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 18px;
            width: 100%;
            max-width: 200px;
            transition: background-color 0.3s, transform 0.3s;
        }
        .login-form input[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #333333;
            color: #ffffff;
            margin-top: 50px;
            position: relative;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="header">
        <img src="images/bank.jpeg" alt="Bank of Paradise Logo">
        <h2>BANK OF PARADISE</h2>
    </div>
    <div class="container">
        <div class="login-form">
            <h2>Admin Login</h2>
            <form action="AdminOperations.jsp" method="post">
                <label for="adminUsername">Username:</label>
                <input type="text" id="adminUsername" name="adminUsername" required><br>
                <label for="adminPassword">Password:</label>
                <input type="password" id="adminPassword" name="adminPassword" required><br>
                <input type="submit" value="Login">
            </form>
        </div>
    </div>
    <div class="footer">
        © 2024 All Rights Reserved by Harsha
    </div>
    <script>
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function (event) {
            event.preventDefault(); // Prevent default back button behavior
            history.pushState(null, null, document.URL);
        });
    </script>
</body>
</html>
