<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="..	/css/style.css" rel="stylesheet" type="text/css">
<title>Display</title>
</head>
<body>
<div align="center">
<h1>Display Voters</h1>
<table border=1>
	<tr>
		<th>SIN</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Birth Date</th>
		<th>Address</th>
		<th>Voted Party</th>
	</tr>
<c:forEach var="voter" items="${voterList}">
	<tr>
		<td>${voter.sin}</td>
		<td>${voter.fname}</td>
		<td>${voter.lname}</td>
		<td>${voter.birthdate}</td>
		<td>${voter.address}</td>
		<c:if test="${empty voter.vote}">
		    <td>N/A</td>
		</c:if>
		<c:if test="${not empty voter.vote}">
		    <td>${voter.vote.votedParty}</td>
		</c:if>
		<td><a href="<c:url value="/edit/${voter.sin }"/>">Edit</a></td>
		<td><a href="<c:url value="/delete/${voter.sin }"/>">Delete</a></td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>