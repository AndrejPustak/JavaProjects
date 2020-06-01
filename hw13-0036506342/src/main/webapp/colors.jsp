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
   		<p><a href="setcolor?pickedBgCol=FFFFFF">WHITE</a></p>
   		<p><a href="setcolor?pickedBgCol=FF0000">RED</a></p>
   		<p><a href="setcolor?pickedBgCol=00FF00">GREEN</a></p>
   		<p><a href="setcolor?pickedBgCol=00FFFF">CYAN</a></p>
   		
   		<br>
   		<p><a href="index.jsp">Back to index.jsp</a></p>
   </body>
</html>