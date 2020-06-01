<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}

String message = String.valueOf(request.getAttribute("error"));

%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		
   		<h2>Error message:</h2>
   		<p><%=message%></p>
   		
   </body>
</html>