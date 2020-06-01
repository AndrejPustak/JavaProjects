<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
%>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}

Map<String, String> names = (Map<String, String>) session.getAttribute("names");

Set<String> ids = names.keySet();
List<String> idsList = new ArrayList<>(ids);
Collections.sort(idsList);

%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		<h1>Glasanje za omiljeni bend:</h1>
		 <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
		glasali!</p>
		 <ol>
		 <%
		 for(String id : idsList){
			 out.println("<li><a href=\"glasanje-glasaj?id=" + id + "\">" + names.get(id) + "</a></li>");
		 }
		 %>
		 </ol>
   </body>
</html>