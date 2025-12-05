<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head><title>User Login</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>User Login</h2>
<c:if test='${not empty message}'><div style="color:red">${message}</div></c:if>
<form action="${pageContext.request.contextPath}/users-ui/login" method="post">
  Username: <input name="userName"/><br/>
  Password: <input type="password" name="password"/><br/>
  <button type="submit">Login</button>
</form>
</body></html>
