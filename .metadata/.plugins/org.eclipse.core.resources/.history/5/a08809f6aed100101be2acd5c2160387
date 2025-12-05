<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Admin Login</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>Admin Login</h2>
<c:if test='${not empty message}'><div style="color:red">${message}</div></c:if>
<form action="${pageContext.request.contextPath}/admins-ui/login" method="post">
  Username: <input name="userName"/><br/>Password: <input type="password" name="password"/><br/>
  <button type="submit">Login</button>
</form>
</body></html>
