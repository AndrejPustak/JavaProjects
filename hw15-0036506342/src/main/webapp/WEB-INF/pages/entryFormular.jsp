<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
   <body>
   		<c:choose>
		    <c:when test="${author == sessionScope['current.user.nick']}">
		        <form action="update" method="post">
		        <input type="hidden" name="author" value='<c:out value="${author}"/>'>
		        <input type="hidden" name="entryID" value='<c:out value="${entryID}"/>'>
   				Title:<br><input type="text" name="title" value='<c:out value="${title}"/>'><br>
   				Text:<br><input type="text" name="text" value='<c:out value="${text}"/>'><br>
		   		<input type="submit" value="Confirm">
   				</form>
		    </c:when>
		    <c:otherwise>
		        <br><a href="logout">Unavailable.</a>
		    </c:otherwise>
		</c:choose>
   </body>
</html>