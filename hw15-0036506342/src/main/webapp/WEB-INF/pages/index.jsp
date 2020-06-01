<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
   <body>
   		<c:choose>
		    <c:when test="${empty sessionScope['current.user.id']}">
		        Not loged-in.
		        <form action="main" method="post">
		   		nick:<br><input type="text" name="nick" value='<c:out value="${nick}"/>'><br>
		   		password:<br><input type="password" name="password"><br>
		   		<c:if test="${not empty requestScope.loginError}">
				 	<c:out value="${requestScope.loginError}"/><br>
				</c:if>
		   		<input type="submit" value="Log-in">
		   		</form>
		    </c:when>
		    <c:otherwise>
		        Loged-in as <c:out value="${sessionScope['current.user.fn']}"/> <c:out value="${sessionScope['current.user.ln']}"/>
		        <br><a href="logout">Log out.</a>
		    </c:otherwise>
		</c:choose>
   		
   		
   		
   		<br><hr>
   		
   		<a href="register">Register new user.</a>
   		
   		<br><hr>
   		
   		<c:forEach items="${users}" var="user">
			   <a href="author/${user.nick}"><c:out value="${user.nick}"/></a><br>
   		</c:forEach>
   		
   </body>
</html>