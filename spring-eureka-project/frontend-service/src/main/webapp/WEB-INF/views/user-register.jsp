<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>

    <!-- Fonts / Icons (optional but matches rest of app) -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: radial-gradient(circle at top, #1e293b 0, #020617 45%, #000 100%);
            margin: 0;
            color: #e5e7eb;
        }
        .container {
            max-width: 820px;
            margin: 40px auto;
            padding: 0 18px;
        }
        .form-card {
            background: rgba(15,23,42,0.96);
            padding: 26px 26px 28px;
            border-radius: 16px;
            border: 1px solid rgba(148,163,184,0.4);
            box-shadow: 0 18px 40px rgba(15,23,42,0.9);
        }
        h2 {
            text-align: center;
            margin-top: 4px;
            margin-bottom: 14px;
            font-size: 22px;
            font-weight: 600;
        }
        h2 i {
            color: #f97316;
            margin-right: 6px;
        }
        .row {
            display: flex;
            gap: 14px;
        }
        .field {
            flex: 1;
            margin-bottom: 14px;
        }
        label {
            display: block;
            font-size: 13px;
            margin-bottom: 4px;
        }
        input, select {
            width: 100%;
            padding: 8px 10px;
            border-radius: 8px;
            border: 1px solid #4b5563;
            background: #020617;
            color: #e5e7eb;
            font-size: 13px;
        }
        input:focus, select:focus {
            outline: none;
            border-color: #6366f1;
            box-shadow: 0 0 0 1px #6366f1;
        }
        .btn-primary {
            width: 100%;
            border: none;
            border-radius: 999px;
            padding: 11px 0;
            font-size: 14px;
            background: linear-gradient(135deg,#6366f1,#f97316);
            color: white;
            cursor: pointer;
            font-weight: 600;
            margin-top: 10px;
        }
        .btn-secondary {
            width: 100%;
            border-radius: 999px;
            padding: 9px 0;
            font-size: 13px;
            background: transparent;
            border: 1px solid #6366f1;
            color: #e5e7eb;
            cursor: pointer;
            margin-top: 10px;
        }
        .msg {
            padding: 8px 10px;
            border-radius: 8px;
            margin-bottom: 12px;
            font-size: 12px;
            text-align: center;
        }
        .success { background: rgba(34,197,94,0.1); border:1px solid rgba(34,197,94,0.6); color:#bbf7d0; }
        .error   { background: rgba(248,113,113,0.12); border:1px solid rgba(248,113,113,0.8); color:#fecaca; }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container">
    <div class="form-card">

        <h2><i class="fa-solid fa-user-plus"></i>User Registration Form</h2>

        <c:if test="${not empty message}">
            <div class="msg success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="msg error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/users-ui/register" method="post">

            <div class="row">
                <div class="field">
                    <label>First Name</label>
                    <input name="firstName" required />
                </div>
                <div class="field">
                    <label>Last Name</label>
                    <input name="lastName" required />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Username</label>
                    <input name="userName" required />
                </div>
                <div class="field">
                    <label>Password</label>
                    <input type="password" name="password" required />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Email</label>
                    <input type="email" name="email" required />
                </div>
                <div class="field">
                    <label>Mobile No</label>
                    <input type="tel" maxlength="10" name="mobileNo" required />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Date of Birth</label>
                    <input type="date" name="dob" />
                </div>
                <div class="field">
                    <label>Account Type</label>
                    <select name="accountType">
                        <option>Saving</option>
                        <option>Current</option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Cheque Facility</label>
                    <select name="cheqFacil">
                        <option>YES</option>
                        <option>NO</option>
                    </select>
                </div>
                <div class="field">
                    <label>Initial Amount</label>
                    <input type="number" name="amount" />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Address Line 1</label>
                    <input name="address1" />
                </div>
                <div class="field">
                    <label>Address Line 2</label>
                    <input name="address2" />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>City</label>
                    <input name="city" />
                </div>
                <div class="field">
                    <label>State</label>
                    <input name="state" />
                </div>
            </div>

            <div class="row">
                <div class="field">
                    <label>Zip Code</label>
                    <input type="number" name="zipCode" />
                </div>
                <div class="field">
                    <label>Country</label>
                    <input name="country" value="India" />
                </div>
            </div>

            <!-- status INACTIVE by default -->
            <input type="hidden" name="status" value="INACTIVE"/>

            <button type="submit" class="btn-primary">Register User</button>
        </form>

        <form action="${pageContext.request.contextPath}/users-ui/login" method="get">
            <button type="submit" class="btn-secondary">Already Registered? Login</button>
        </form>

    </div>
</div>

</body>
</html>
