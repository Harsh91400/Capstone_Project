<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Your Apps</title>
</head>
<body>
<h2>Apps created by ${owner}</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<c:if test="${empty apps}">
    <p>No apps found.</p>
</c:if>

<ul>
    <c:forEach var="app" items="${apps}">
        <li>${app}</li>
    </c:forEach>
</ul>

</body>
</html>
