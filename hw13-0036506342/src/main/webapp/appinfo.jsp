<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
private String getTime(HttpServletRequest req){
	long start = (long) req.getServletContext().getAttribute("startTime");
	long current = System.currentTimeMillis();
	
	long duration = current - start;
	
	long days = duration/86400000;
	duration = duration - days*86400000;
	
	long hours = duration / 3600000;
	duration = duration - hours*3600000;
	
	long minutes = duration / 60000;
	duration = duration - minutes * 60000;
	
	long seconds = duration / 1000;
	duration = duration - seconds * 1000;
	
	long milisec = duration;
	
	return ""  + days + " days " + hours + " hours " + minutes + " minutes " 
				+ seconds + " seconds " + milisec + " milliseconds";
	
}
%>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}

%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		
   		<h2>Time running: </h2>
   		<p><%=getTime(request)%></p>
   		
   </body>
</html>