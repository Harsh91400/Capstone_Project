<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Add App</title></head><body>
<jsp:include page="navbar.jsp" />
<h2>Add App</h2>
<c:if test='${not empty message}'><div style="color:green">${message}</div></c:if>
<form action="${pageContext.request.contextPath}/owners-ui/add-app" method="post">
  App name: <input name="appName"/><br/>
  App type: <input name="appType"/><br/>
  Description: <input name="description"/><br/>
  Genre: <input name="genre"/><br/>
  Rating: <input name="rating"/><br/>
  Release date: <input type="date" name="releaseDate"/><br/>
  Version: <input name="version"/><br/>
  <button type="submit">Add App</button>
</form>
</body></html>
