<%@page import="java.util.Map.Entry"%>
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
Map<String, String> links = (Map<String, String>) session.getAttribute("links");

Set<String> ids = names.keySet();
List<String> idsList = new ArrayList<>(ids);
Collections.sort(idsList);

Map<String, Integer> results = (Map<String, Integer>) request.getAttribute("results");
List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(results.entrySet());
entries.sort(Entry.comparingByValue());

session.setAttribute("entries", entries);
Integer max = entries.get(entries.size()-1).getValue();

%>

<!DOCTYPE html>
<html>
	 <head>
		 <style type="text/css">
		 	table.rez td {text-align: center;}
		 </style>
	 </head>
 <body bgcolor="#<%=color%>">

	 <h1>Rezultati glasanja</h1>
	 <p>Ovo su rezultati glasanja.</p>
	 <table border="1" cellspacing="0" class="rez">
	 <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	 <tbody>
	 	<%
	 	for(int i = entries.size() - 1; i>= 0; i--){
	 		Entry<String, Integer> entry = entries.get(i);
	 		out.println("<tr><td>" + names.get(entry.getKey()) + "</td><td>" + entry.getValue() + "</td></tr>");
	 	}
	 	%>
	 </tbody>
	 </table>
	
	 <h2>Grafički prikaz rezultata</h2>
	 <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	
	 <h2>Rezultati u XLS formatu</h2>
	 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
	
	 <h2>Razno</h2>
	 <p>Primjeri pjesama pobjedničkih bendova:</p>
	 <ul>
	 <%
	 for(Entry<String, Integer> entry : entries){
		 if(entry.getValue().equals(max))
		 	out.println("<li><a href=\"" + links.get(entry.getKey()) + "\" target=\"_blank\">"
		 	+ names.get(entry.getKey()) + "</a></li>");
	 }
	 
	 %>
	 </ul>
 </body>
</html>
