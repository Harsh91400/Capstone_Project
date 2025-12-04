<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="background:linear-gradient(90deg,#ffd6a5,#ffadad);padding:12px;border-radius:6px;margin-bottom:12px;">
  <div style="display:flex;justify-content:space-between;align-items:center;">
    <div>
      <a href="${pageContext.request.contextPath}/" style="font-weight:bold;text-decoration:none;color:#3a3a3a">App Store</a>
    </div>
    <div>
      <c:choose>
        <c:when test="${not empty sessionScope.role and sessionScope.role == 'ADMIN'}">
          <a href="${pageContext.request.contextPath}/admins-ui/users">Users</a> |
          <a href="${pageContext.request.contextPath}/owners-ui/register">Add Owner</a> |
          <a href="${pageContext.request.contextPath}/">Apps</a> |
          <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:when test="${not empty sessionScope.role and sessionScope.role == 'OWNER'}">
          <a href="${pageContext.request.contextPath}/">Apps</a> |
          <a href="${pageContext.request.contextPath}/owners-ui/add-app">Add App</a> |
          <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:when test="${not empty sessionScope.role and sessionScope.role == 'USER'}">
          <a href="${pageContext.request.contextPath}/">Apps</a> |
          <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/">Home</a> |
          <a href="${pageContext.request.contextPath}/users-ui/register">User Register</a> |
          <a href="${pageContext.request.contextPath}/users-ui/login">User Login</a> |
          <a href="${pageContext.request.contextPath}/owners-ui/register">Owner Register</a> |
          <a href="${pageContext.request.contextPath}/owners-ui/login">Owner Login</a> |
          <a href="${pageContext.request.contextPath}/admins-ui/login">Admin Login</a>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
