<%@page import="hr.fer.zemris.java.p12.model.PollOptionInput"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.p12.model.PollInput"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%

PollInput poll = (PollInput) session.getAttribute("poll");
List<PollOptionInput> inputs = (List<PollOptionInput>) request.getAttribute("inputs");

%>

<!DOCTYPE html>
<html>
   <body>
   		<h1><%=poll.getTitle()%></h1>
		 <p><%=poll.getMessage() %></p>
		 <ol>
		 <%
		 for(PollOptionInput input : inputs){
			 out.println("<li><a href=\"glasanje-glasaj?id=" + input.getId() + "\">" + input.getOptionTitle() + "</a></li>");
		 }
		 %>
		 </ol>
   </body>
</html>