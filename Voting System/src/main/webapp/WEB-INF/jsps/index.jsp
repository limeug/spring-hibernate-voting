<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
<link href="..	/css/style.css" rel="stylesheet" type="text/css">
<base href="/">
</head>
<body>
<div align="center">
<h1>Election Main Page</h1>
	<h5>${msg }</h5>
	<form:form action="/Register">
		<input type="submit" name="btn" value="Register"/>
	</form:form>
	</br>
	<form:form action="/Vote">
		<input type="submit" name="btn" value="Vote"/>
	</form:form>
	</br>
	<form:form action="/DummyRecordsGenerated">
		<input type="submit" name="btn" value="Generate Records"/>
	</form:form>
	</br>
	<form:form action="/View">
		<input type="submit" name="btn" value="View Voters"/>
	</form:form>
	</br>
	<form:form action="/Stats">
		<input type="submit" name="btn" value="View Stats"/>
	</form:form>
	</br>
</div>
</body>
</html>