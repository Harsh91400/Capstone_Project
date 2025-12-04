<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head><title>User Register</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>User Register</h2>
<c:if test='${not empty message}'><div style="color:green">${message}</div></c:if>
<form action="${pageContext.request.contextPath}/users-ui/register" method="post">
  First name: <input name="firstName"/><br/>
  Last name: <input name="lastName"/><br/>
  Username: <input name="userName"/><br/>
  Password: <input type="password" name="password"/><br/>
  Email: <input name="email"/><br/>
  Mobile: <input name="mobileNo"/><br/>
  Dob: <input type="date" name="dob"/><br/>
  <button type="submit">Register</button>
</form>
</body></html>
