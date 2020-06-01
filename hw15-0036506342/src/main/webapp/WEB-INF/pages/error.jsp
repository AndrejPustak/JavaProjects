<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
   <body>
   		<h1>Error message:</h1><br>
   		<h2><c:out value="${error}"/></h2>
   </body>
</html>