<%@page import="java.util.Comparator"%>
<%@page import="hr.fer.zemris.java.p12.model.PollOptionInput"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
List<PollOptionInput> inputs = (List<PollOptionInput>) session.getAttribute("inputs");
Comparator<PollOptionInput> optionInputComparator = (p1, p2) -> Long.compare(p1.getVotesCount(), p2.getVotesCount());
optionInputComparator = optionInputComparator.reversed();
inputs.sort(optionInputComparator);

long max = inputs.get(0).getVotesCount(); 

%>

<!DOCTYPE html>
<html>
	 <head>
		 <style type="text/css">
		 	table.rez td {text-align: center;}
		 </style>
	 </head>
 <body>

	 <h1>Rezultati glasanja</h1>
	 <p>Ovo su rezultati glasanja.</p>
	 <table border="1" cellspacing="0" class="rez">
	 <tbody>
	 	<%
	 	for(PollOptionInput input : inputs){
	 		out.println("<tr><td>" + input.getOptionTitle() + "</td><td>" + input.getVotesCount() + "</td></tr>");
	 	}
	 	%>
	 </tbody>
	 </table>
	
	 <h2>Grafički prikaz rezultata</h2>
	 <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	
	 <h2>Rezultati u XLS formatu</h2>
	 <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
	
	 <h2>Razno</h2>
	 <p>Pobjednici polla sa linkovima:</p>
	 <ul>
	 <%
	 for(PollOptionInput input : inputs){
		 if(input.getVotesCount() == max)
		 	out.println("<li><a href=\"" + input.getOptionLink() + "\" target=\"_blank\">"
		 	+ input.getOptionTitle() + "</a></li>");
	 }
	 
	 %>
	 </ul>
 </body>
</html>
