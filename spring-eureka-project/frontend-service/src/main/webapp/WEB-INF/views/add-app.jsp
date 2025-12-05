<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add App</title>

    <!-- Fonts + Icons -->
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
            max-width: 700px;
            margin: 40px auto 60px;
            padding: 22px;
            background: rgba(15,23,42,0.96);
            border-radius: 16px;
            border: 1px solid rgba(148,163,184,0.35);
            box-shadow: 0 20px 50px rgba(15,23,42,0.9);
        }

        h2 {
            text-align: center;
            margin-top: 0;
            margin-bottom: 20px;
            color: #e2e8f0;
            font-weight: 600;
        }

        .row {
            display: flex;
            gap: 14px;
        }

        .field {
            flex: 1;
            margin-bottom: 16px;
        }

        label {
            display: block;
            font-size: 13px;
            margin-bottom: 6px;
            color: #cbd5e1;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid rgba(148,163,184,0.35);
            background: #020617;
            color: #e5e7eb;
            font-size: 14px;
        }

        textarea {
            height: 70px;
            resize: none;
        }

        input:focus, textarea:focus {
            outline: none;
            border-color: #6366f1;
            box-shadow: 0 0 0 1px #6366f1;
            background: rgba(6,10,25,0.9);
        }

        /* ==== DATE CALENDAR ICON FIX ==== */
        input[type="date"] {
            color-scheme: dark;
        }
        input[type="date"]::-webkit-calendar-picker-indicator {
            filter: invert(1);
            cursor: pointer;
        }

        /* ==== BUTTON ==== */
        .btn-submit {
            width: 100%;
            background: linear-gradient(135deg, #6366f1, #f97316);
            padding: 12px;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            color: white;
            font-weight: 600;
            font-size: 16px;
            margin-top: 8px;
        }

        .btn-submit:hover {
            box-shadow: 0 0 12px rgba(99,102,241,0.8);
        }

        /* ==== MESSAGE ==== */
        .msg {
            padding: 10px;
            border-radius: 10px;
            font-size: 13px;
            margin-bottom: 16px;
            text-align: center;
        }

        .success {
            background: rgba(34,197,94,0.14);
            border: 1px solid rgba(34,197,94,0.7);
            color: #4ade80;
        }

        .error {
            background: rgba(248,113,113,0.14);
            border: 1px solid rgba(248,113,113,0.8);
            color: #fecaca;
        }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container">

    <h2><i class="fa-solid fa-square-plus" style="color:#f97316"></i> Add App</h2>

    <!-- Messages -->
    <c:if test="${not empty message}">
        <div class="msg success">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="msg error">${error}</div>
    </c:if>

    <!-- Form -->
    <form action="${pageContext.request.contextPath}/owners-ui/add-app" method="post">

        <div class="row">
            <div class="field">
                <label>App Name</label>
                <input name="appName" required />
            </div>
            <div class="field">
                <label>App Type</label>
                <input name="appType" required />
            </div>
        </div>

        <div class="field">
            <label>Description</label>
            <textarea name="description" placeholder="Describe your application..." required></textarea>
        </div>

        <div class="row">
            <div class="field">
                <label>Genre</label>
                <input name="genre" placeholder="Tools, Game, Social, Music etc." required />
            </div>
            <div class="field">
                <label>Rating</label>
                <input name="rating" type="number" step="0.1" min="0" max="5" />
            </div>
        </div>

        <div class="row">
            <div class="field">
                <label>Release Date</label>
                <input type="date" name="releaseDate" required />
            </div>
            <div class="field">
                <label>Version</label>
                <input name="version" required placeholder="1.0.0" />
            </div>
        </div>

        <button type="submit" class="btn-submit">+ Add App</button>
    </form>

</div>

</body>
</html>
