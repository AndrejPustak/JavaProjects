<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
   <body>
   		<c:choose>
		    <c:when test="${empty sessionScope['current.user.id']}">
		        <form action="register" method="post">
   				First name:<br><input type="text" name="fn" value='<c:out value="${fn}"/>'><br>
   				<c:if test="${not empty requestScope['error.fn']}">
				 	<c:out value="${requestScope['error.fn']}"/><br>
				</c:if>
   				Last name:<br><input type="text" name="ln" value='<c:out value="${ln}"/>'><br>
   				<c:if test="${not empty requestScope['error.ln']}">
				 	<c:out value="${requestScope['error.ln']}"/><br>
				</c:if>
   				E-Mail:<br><input type="text" name="email" value='<c:out value="${email}"/>'><br>
   				<c:if test="${not empty requestScope['error.email']}">
				 	<c:out value="${requestScope['error.email']}"/><br>
				</c:if>
		   		nick:<br><input type="text" name="nick"><br>
		   		<c:if test="${not empty requestScope['error.nick']}">
				 	<c:out value="${requestScope['error.nick']}"/><br>
				</c:if>
		   		password:<br><input type="password" name="password"><br>
		   		<c:if test="${not empty requestScope['error.pw']}">
				 	<c:out value="${requestScope['error.pw']}"/><br>
				</c:if>
		   		<input type="submit" value="Register">
   				</form>
		    </c:when>
		    <c:otherwise>
		        Already loged-in as <c:out value="${sessionScope['current.user.fn']}"/> <c:out value="${sessionScope['current.user.ln']}"/>
		        <br><a href="logout">Log out.</a>
		    </c:otherwise>
		</c:choose>
   </body>
</html>