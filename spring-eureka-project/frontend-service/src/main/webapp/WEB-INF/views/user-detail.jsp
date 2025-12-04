<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>User Detail</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>User Detail</h2>
<c:if test='${not empty error}'><div style="color:red">${error}</div></c:if>
<c:if test='${not empty user}'>
  <div>Id: ${user.userId}</div>
  <div>Name: ${user.firstName} ${user.lastName}</div>
  <div>Username: ${user.userName}</div>
  <div>Email: ${user.email}</div>
  <div>Mobile: ${user.mobileNo}</div>
</c:if>
</body></html>
