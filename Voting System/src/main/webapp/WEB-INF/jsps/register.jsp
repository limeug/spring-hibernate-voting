<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="..	/css/style.css" rel="stylesheet" type="text/css">
<title>Register</title>
</head>
<body>
<div align="center" >
	<h1>Register Page</h1>
	<h3>Please Enter the information below to register for voting</h3>
	<div id ="middle">
	<c:url var="url" value="/Registered"/>
	<form:form action="${url }" modelAttribute="voter">
		<table border=1>
			<tr>
				<td>First Name:</td>
				<td><form:input path="fname" /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><form:input path="lname" /></td>
			</tr>
			<tr>
				<td>Birth date (YYYY-MM-DD):</td>
				<td><form:input path="birthdate" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><form:input path="address" /></td>
			</tr>
			<tr>
				<td>Social Insurance Number:</td>
				<td><form:input path="sin" /></td>
			</tr>
		</table>
		</br>
		<input type="submit" value="Register" />
	</form:form>
	</div>
	<h5>${msg }</h5>
</div>
</body>
</html>