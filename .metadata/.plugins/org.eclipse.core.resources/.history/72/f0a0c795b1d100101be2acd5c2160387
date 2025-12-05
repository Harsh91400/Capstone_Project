<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Owner Register</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>Owner Register</h2>
<c:if test='${not empty message}'><div style="color:green">${message}</div></c:if>
<form action="${pageContext.request.contextPath}/owners-ui/register" method="post">
  First: <input name="firstName"/><br/>Last: <input name="lastName"/><br/>
  Username: <input name="userName"/><br/>Password: <input type="password" name="password"/><br/>
  Email: <input name="email"/><br/>Mobile: <input name="mobile"/><br/>
  Address1: <input name="address1"/><br/>Address2: <input name="address2"/><br/>
  City: <input name="city"/><br/>State: <input name="state"/><br/>Zip: <input name="zipCode"/><br/>
  Country: <input name="country" value="India"/><br/>
  <button type="submit">Register</button>
</form>
</body></html>
