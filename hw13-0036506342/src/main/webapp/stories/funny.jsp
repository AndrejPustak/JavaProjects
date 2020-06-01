<%@page import="java.util.Random"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
private String getColor(){
	Random rand = new Random();
	
	StringBuilder sb = new StringBuilder();
	for(int i = 0 ; i<6; i++){
		sb.append(Integer.toHexString(Math.abs(rand.nextInt())%16));
	}
	
	return sb.toString();
}
%>

<%
String color = String.valueOf(session.getAttribute("pickedBgCol"));
if(color.equals("null")){
	color = "FFFFFF";
}

String fontColor = getColor();
%>

<!DOCTYPE html>
<html>
   <body bgcolor="#<%=color%>">
   		<p><font color="#<%=fontColor%>">During the heat of the space race in the 1960's, NASA quickly discovered that ballpoint pens would not work in the zero gravity confines of its space capsules. After considerable research and development, the Astronaut Pen was developed at a cost of $1 million. The pen worked in zero gravity, upside down, underwater, on almost any surface including glass and also enjoyed some modest success as a novelty item back here on earth. 
        <br>The Soviet Union, when faced with the same problem, used a pencil.</font></p>
   </body>
</html>