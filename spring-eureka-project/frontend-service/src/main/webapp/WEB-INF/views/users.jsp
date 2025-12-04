<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Users</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>Users List</h2>
<c:if test='${not empty error}'><div style="color:red">${error}</div></c:if>
<c:forEach var="u" items="${users}">
  <div style="border:1px solid #ddd;padding:8px;margin:6px;">
    <b>${u.firstName} ${u.lastName}</b> - ${u.userName} - ${u.email}
    <a href="${pageContext.request.contextPath}/users-ui/${u.userId}">Show Details</a>
  </div>
</c:forEach>
</body></html>
