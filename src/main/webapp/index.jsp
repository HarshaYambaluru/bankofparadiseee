<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>BANK OF PARADISE</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #ffffff;
            color: #333333;
            margin: 0;
            padding: 0;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px;
        }
        .container h1 {
            font-size: 48px;
            color: #333333;
            margin-bottom: 40px;
        }
        .btn {
            display: inline-block;
            margin: 20px;
            padding: 15px 30px;
            font-size: 18px;
            text-decoration: none;
            color: #ffffff;
            background-color: #007bff;
            border-radius: 8px;
            transition: background-color 0.3s, transform 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 40px;
            background-color: #f8f9fa;
            border-bottom: 2px solid #e9ecef;
        }
        .header img {
            height: 60px;
        }
        .header h2 {
            font-size: 32px;
            color: #333333;
        }
        .login-container {
            display: flex;
            justify-content: center;
            width: 100%;
            margin-top: 20px;
        }
        .login-box {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 20px;
            padding: 30px;
            border: 1px solid #e9ecef;
            border-radius: 10px;
            background-color: #f8f9fa;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }
        .login-box:hover {
            transform: translateY(-5px);
        }
        .login-box img {
            height: 120px;
            margin-bottom: 20px;
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
        <h1>Welcome to BANK OF PARADISE</h1>
        <div class="login-container">
            <div class="login-box">
                <img src="images/002.png" alt="User Login">
                <a href="customerlogin.jsp" class="btn">User Login</a>
            </div>
            <div class="login-box">
                <img src="images/003.png" alt="Admin Login">
                <a href="AdminLogin.jsp" class="btn">Admin Login</a>
            </div>
        </div>
    </div>
    <div class="footer">
        &copy; 2024 All Rights Reserved by Harsha
    </div>
    <script>
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>
</body>
</html>
