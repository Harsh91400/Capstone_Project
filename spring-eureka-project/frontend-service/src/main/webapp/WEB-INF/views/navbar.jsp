<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    .nav-main {
        background: rgba(2,6,23,0.95);
        backdrop-filter: blur(10px);
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 28px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.45);
        font-family: 'Poppins', sans-serif;
        border-bottom: 1px solid rgba(148,163,184,0.25);
    }

    .nav-logo {
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 20px;
        font-weight: 600;
        color: #e5e7eb;
        cursor: pointer;
        text-decoration: none;
    }

    .nav-logo i {
        font-size: 22px;
        color: #f97316;
        background: rgba(15,23,42,0.8);
        padding: 8px;
        border-radius: 10px;
    }

    .nav-links a {
        color: #cbd5f5;
        font-size: 14px;
        text-decoration: none;
        margin-left: 22px;
        transition: 0.2s ease;
        padding: 6px 10px;
        border-radius: 8px;
    }

    .nav-links a:hover {
        color: #fff;
        background: rgba(99,102,241,0.22);
    }
</style>

<!-- Include Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

<div class="nav-main">
    <a href="${pageContext.request.contextPath}/" class="nav-logo">
        <i class="fa-solid fa-store"></i> App Store
    </a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/users-ui/login">User</a>
        <a href="${pageContext.request.contextPath}/owners-ui/login">Owner</a>
        <a href="${pageContext.request.contextPath}/admins-ui/login">Admin</a>

        <c:if test="${not empty sessionScope.userName}">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
    </div>
</div>
