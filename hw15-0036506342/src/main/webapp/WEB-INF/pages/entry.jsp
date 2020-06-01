<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
   <body>
   		<c:choose>
	   		<c:when test="${empty sessionScope['current.user.id']}">
			        Not loged-in.
			</c:when>
			<c:otherwise>
		    	Loged-in as <c:out value="${sessionScope['current.user.fn']}"/> <c:out value="${sessionScope['current.user.ln']}"/>
			    <br><a href="${pageContext.request.contextPath}/servleti/logout">Log out.</a>
			</c:otherwise>
		</c:choose>
   		<br><hr>
   		<h1><c:out value="${entry.title}"/></h1>
   		<hr>
   		<h3><p><c:out value="${entry.text}"/></p></h3>
   		<hr>
   		<c:if test="${author == sessionScope['current.user.nick']}">
   			Click <a href="edit?id=${entry.id}">here</a> to edit the entry.<br><hr>
   		</c:if>
   		
   		Comments: <br>
   		<c:forEach items="${comments}" var="comment">
			  <c:out value="${comment.usersEMail}"/>[<c:out value="${comment.postedOn}"/>]<br>
			  <c:out value="${comment.message}"/>
			  <hr>
   		</c:forEach>
   		
   		<hr>
   		<form action="${entry.id}" method="post">
   			<c:if test="${empty sessionScope['current.user.id']}">
   				Email:<br><input type="text" name="email" value=""><br>
   				<c:if test="${not empty requestScope['error.email']}">
				 	<c:out value="${requestScope['error.email']}"/><br>
				</c:if>
	  		</c:if>
	  		Comment:<br><textarea type="text" name="message" rows="5" cols="40"></textarea><br>
	  		<c:if test="${not empty requestScope['error.message']}">
				 <c:out value="${requestScope['error.message']}"/><br>
			</c:if>
	  		<input type="submit" value="Post">
   		</form>
   		
   </body>
</html>