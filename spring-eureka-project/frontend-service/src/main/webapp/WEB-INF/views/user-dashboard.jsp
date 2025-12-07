<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Dashboard</title>

    <!-- Google Fonts + Icons -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

    <style>
        :root {
            --bg-main: #050816;
            --bg-card: rgba(15, 23, 42, 0.95);
            --accent: #6366f1;
            --accent-soft: rgba(99, 102, 241, 0.15);
            --text-main: #e5e7eb;
            --text-muted: #9ca3af;
            --danger: #ef4444;
            --success: #22c55e;
            --border-soft: rgba(148, 163, 184, 0.25);
        }

        * { box-sizing: border-box; }

        body {
            margin: 0;
            padding: 0;
            font-family: 'Poppins', sans-serif;
            background: radial-gradient(circle at top, #1e293b 0, #020617 45%, #000 100%);
            color: var(--text-main);
        }

        .container {
            width: 96%;
            max-width: 1240px;
            margin: 0 auto;
            padding: 20px 0 40px;
        }

        .welcome-box {
            margin-top: 10px;
            background: linear-gradient(135deg, rgba(15, 23, 42, 0.95), rgba(30, 64, 175, 0.9));
            border-radius: 14px;
            padding: 18px 22px;
            box-shadow: 0 18px 40px rgba(15, 23, 42, 0.7);
            border: 1px solid rgba(148, 163, 184, 0.35);
            font-size: 18px;
        }
        .welcome-box span { font-weight: 600; color: #facc15; }

        .user-profile-card {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-top: 14px;
            background: linear-gradient(135deg, rgba(15, 23, 42, 0.95), rgba(30, 64, 175, 0.85));
            padding: 14px 18px;
            border-radius: 14px;
            border: 1px solid rgba(148, 163, 184, 0.35);
            box-shadow: 0 16px 35px rgba(15, 23, 42, 0.65);
        }
        .user-profile-card i {
            font-size: 32px;
            color: #22c55e;
            background: rgba(15, 23, 42, 0.8);
            padding: 10px;
            border-radius: 999px;
        }
        .user-info h3 { margin: 0; font-size: 19px; font-weight: 600; }
        .user-info p { margin: 2px 0 0; font-size: 13px; color: var(--text-muted); }

        .flash {
            margin-top: 12px;
            padding: 8px 12px;
            border-radius: 10px;
            font-size: 13px;
        }
        .flash-success {
            background: rgba(34,197,94,0.12);
            border: 1px solid rgba(34,197,94,0.7);
            color: #bbf7d0;
        }
        .flash-error {
            background: rgba(248,113,113,0.14);
            border: 1px solid rgba(248,113,113,0.8);
            color: #fecaca;
        }

        .section-title {
            font-size: 15px;
            font-weight: 600;
            margin: 20px 0 8px;
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            background: var(--accent-soft);
            border: 1px solid rgba(99, 102, 241, 0.4);
        }

        .table-wrapper {
            overflow: hidden;
            border-radius: 14px;
            border: 1px solid var(--border-soft);
            background: var(--bg-card);
            box-shadow: 0 16px 40px rgba(15, 23, 42, 0.9);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            color: var(--text-main);
            font-size: 13px;
        }
        th, td {
            padding: 8px 10px;
            border-bottom: 1px solid rgba(31, 41, 55, 0.9);
        }
        th {
            text-align: left;
            background: rgba(15, 23, 42, 0.96);
            font-size: 12px;
            color: #cbd5f5;
        }
        tr:nth-child(even) td { background: rgba(15, 23, 42, 0.92); }
        tr:nth-child(odd) td  { background: rgba(15, 23, 42, 0.98); }

        .btn-download {
            padding: 4px 10px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            font-size: 11px;
            color: #f9fafb;
            font-weight: 500;
            background: linear-gradient(135deg, #6366f1, #f97316);
        }
        .btn-download:disabled {
            opacity: 0.6;
            cursor: default;
        }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="container">

    <!-- Welcome -->
    <div class="welcome-box">
        Hello, <span>${sessionScope.userName}</span> 👋 &nbsp;Welcome to your dashboard
    </div>

    <!-- User profile card -->
    <div class="user-profile-card">
        <i class="fa-solid fa-user"></i>
        <div class="user-info">
            <h3>${sessionScope.userName}</h3>
            <p>Logged in user · Browse and download apps</p>
        </div>
    </div>

    <!-- FLASH MESSAGE -->
    <c:if test="${not empty message}">
        <div class="flash flash-success">
            ${message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="flash flash-error">
            ${error}
        </div>
    </c:if>

    <!-- ================== ALL APPS ================== -->
    <div class="section-title">All Apps</div>
    <div class="table-wrapper">
        <table>
            <tr>
                <th>APP_ID</th>
                <th>App Name</th>
                <th>Type</th>
                <th>Genre</th>
                <th>Action</th>
            </tr>
            <c:forEach var="a" items="${apps}">
                <tr>
                    <td>${a.appId}</td>
                    <td>${a.appName}</td>
                    <td>${a.appType}</td>
                    <td>${a.genre}</td>
                    <td>
                        <!-- form -> USER_APP me POST -->
                        <form action="${pageContext.request.contextPath}/users-ui/apps/${a.appId}/download"
                              method="post" style="display:inline">
                            <button type="submit" class="btn-download">
                                Download
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <!-- ================== MY DOWNLOADED APPS ================== -->
    <div class="section-title">My Downloaded Apps</div>
    <div class="table-wrapper">
        <table>
            <tr>
                <th>USER_APP_ID</th>
                <th>APP_ID</th>
                <th>DOWNLOAD_DATE</th>
                <th>USER_ID</th>
            </tr>
            <c:forEach var="d" items="${downloads}">
                <tr>
                    <td>${d.userAppId}</td>
                    <td>${d.appId}</td>
                    <td>${d.downloadDate}</td>
                    <td>${d.userId}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>
</body>
</html>
