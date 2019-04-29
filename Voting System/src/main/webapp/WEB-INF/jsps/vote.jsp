<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="..	/css/style.css" rel="stylesheet" type="text/css">
<title>Vote</title>
</head>
<body>
<div align="center">
	<h1>Vote Page</h1>
	
	<c:url var="url" value="/Voting"/>
	<form:form action="${url }" modelAttribute="voter">
		<h3>Please enter your SIN: </h3> 
		<form:input path="sin"/> <br/>
		<input type="submit" value="Check SIN"/>
	</form:form>
	
	<c:if test="${valid}">
		<c:url var="url" value="/Voting/${voter.getSin()} "/>
		<form:form action="${url }" modelAttribute="vote">
			</br>
			<h3>Select the party you would like to vote for: </h3>
			<form:select path="votedParty" items="${vote.voteList}" />
			<input type="submit" value="Vote"/>
		</form:form>
	</c:if>
	</br>
	${msg }
	</br>
</div>
</body>
</html>