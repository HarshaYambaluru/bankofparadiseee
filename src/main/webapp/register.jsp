<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            color: #333333;
            margin: 0;
            padding: 20px;
        }
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .form-container h2 {
            text-align: center;
            color: #343a40;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
            color: #343a40;
        }
        .form-container input[type="text"],
        .form-container input[type="email"],
        .form-container input[type="number"],
        .form-container input[type="date"],
        .form-container select,
        .form-container input[type="radio"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #dee2e6;
            border-radius: 5px;
        }
        .form-container input[type="submit"] {
            background-color: #007bff;
            color: #ffffff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
            width: 100%;
        }
        .form-container input[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
        }
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #343a40;
            color: #ffffff;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Customer Registration</h2>
        <form id="registrationForm" action="CustomerRegistrationServlet" method="post" onsubmit="return validateForm()">
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required>
            
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required>
            
            <label for="mobileNo">Mobile No:</label>
            <input type="text" id="mobileNo" name="mobileNo" required>
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            
            <label for="accountType">Account Type:</label>
            <select id="accountType" name="accountType" required>
                <option value="Saving">Saving</option>
                <option value="Current">Current</option>
            </select>
            
            <label for="initialBalance">Initial Balance:</label>
            <input type="number" id="initialBalance" name="initialBalance" min="1000" required>
            
            <label for="dob">Date of Birth:</label>
            <input type="date" id="dob" name="dob" required>
            
            <div style="display: flex;" class="radio-group">
                <label class="radio-label">ID Proof:</label>
                <input type="radio" id="aadhar" name="idProof" value="Aadhar" required>
                <label for="aadhar">Aadhar</label>
                <input type="radio" id="pan" name="idProof" value="PAN" required>
                <label for="pan">PAN</label>
            </div>
            
            <input type="submit" value="Register">
        </form>
    </div>
    
    <div class="footer">
        &copy; 2024 All Rights Reserved by Harsha
    </div>
    
    <script>
        function validateForm() {
            var fullName = document.getElementById("fullName").value;
            var address = document.getElementById("address").value;
            var mobileNo = document.getElementById("mobileNo").value;
            var email = document.getElementById("email").value;
            var initialBalance = document.getElementById("initialBalance").value;
            var dob = document.getElementById("dob").value;

            // Validation for Full Name: Should only contain alphabets and spaces
            var nameRegex = /^[A-Za-z\s]+$/;
            if (!nameRegex.test(fullName)) {
                alert("Full Name should only contain alphabets and spaces.");
                return false;
            }

            // Validation for Mobile No: Should contain only digits and be 10 digits long
            var mobileRegex = /^\d{10}$/;
            if (!mobileRegex.test(mobileNo)) {
                alert("Mobile No should contain 10 digits. Check and try again!!");
                return false;
            }

            // Validation for Date of Birth: Should not be after the present date
            var currentDate = new Date();
            var dobDate = new Date(dob);
            if (dobDate > currentDate) {
                alert("Invalid Date of Birth");
                return false;
            }

            // Additional validation can be added here, such as checking the format of email, initial balance, etc.

            // Basic validation for empty fields
            if (fullName == "" || address == "" || mobileNo == "" || email == "" || initialBalance == "" || dob == "") {
                alert("All fields are required!");
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
