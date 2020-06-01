<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}

int a = Integer.valueOf(String.valueOf(request.getAttribute("varA")));
int b = Integer.valueOf(String.valueOf(request.getAttribute("varB")));

List<String> sin = (ArrayList<String>) request.getAttribute("sin");
List<String> cos = (ArrayList<String>) request.getAttribute("cos");

%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		
   		<table border="1">
   			<tr><td>Angle</td><td>sin</td><td>cos</td></tr>
   			<%
   			for(int i = 0; i<=b-a; i++){
   				out.println("<tr><td>" + String.format("%d", a+i) + "</td><td>" + sin.get(i) + "</td><td>" + cos.get(i) + "</td></tr>");
   			}
   			%>
   		</table>
   		
   </body>
</html>