<%@page import="hr.fer.zemris.java.p12.model.PollInput"%>
<%@page import="java.util.List"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%

List<PollInput> inputs = (List<PollInput>) request.getAttribute("inputs");

%>

<!DOCTYPE html>
<html>
   <body>
   		
   		<h2>List of all the polls:</h2>
   		
 		<%
 		for(PollInput input: inputs){
 			out.print("<a href=\"glasanje?pollID=" + input.getId() + "\">" + input.getTitle() + "</a><br>");	
 		}
 		%>
   		
   </body>
</html>