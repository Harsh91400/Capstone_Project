<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Apps</title>
  <style>
    body{font-family:Arial;max-width:900px;margin:18px auto}
    .card{border:1px solid #eee;padding:12px;margin:8px;border-radius:8px;background:#fff}
  </style>
</head>
<body>
  <jsp:include page="navbar.jsp" />
  <h2>Apps</h2>
  <form action="${pageContext.request.contextPath}/apps/genre" method="get">
    <input name="genre" placeholder="Search by genre (e.g. Games)" />
    <button type="submit">Search</button>
  </form>
  <c:if test="${not empty error}">
    <div style="color:red">${error}</div>
  </c:if>
  <c:forEach var="app" items="${apps}">
    <div class="card">
      <h3>${app.appName}</h3>
      <div>Genre: ${app.genre} | Type: ${app.appType} | Rating: ${app.rating}</div>
      <div>${app.description}</div>
    </div>
  </c:forEach>
</body>
</html>
