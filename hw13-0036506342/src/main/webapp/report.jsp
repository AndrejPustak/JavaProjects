<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}
%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		<h1>OS usage</h1>
   		<p>Here are the results of OS usage in survey that we completed <br>
   		<img alt="report" src="reportImage">
   		</p>
   </body>
</html>