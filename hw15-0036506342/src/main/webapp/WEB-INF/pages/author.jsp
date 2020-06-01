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
   		List of all the author's blog entries:
   		<br><hr>
   		
   		<c:forEach items="${entries}" var="entry">
			   <a href="${author}/${entry.id}"><c:out value="${entry.title}"/></a><br><hr>
   		</c:forEach>
   		
   		<c:if test="${author == sessionScope['current.user.nick']}">
   			Click <a href="${author}/new">here</a> to create a new entry.
   		</c:if>
   		
   </body>
</html>