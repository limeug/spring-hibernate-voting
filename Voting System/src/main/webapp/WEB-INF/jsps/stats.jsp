<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="..	/css/style.css" rel="stylesheet" type="text/css">
<title>Stats</title>
</head>
<body>
<div align="center">
	<h1>Stats</h1>
	<h3>Vote Breakdown</h3>
	Liberal Party: ${liberalP }% </br>
	Conservative Party: ${conP} % </br>
	New Democratic Party: ${ndpP }%</br>
	Bloc Quebecois: ${bqP }%</br>
	Green Party: ${gpP }%</br>
	</br>
	<h3>Percentage of eligible voters that voted: ${votesToVoters }%</h3>
	<h3>Voter Breakdown by Age Group</h3>
	18-30: ${youngAdultP }%</br>
	30-45: ${adultP }%</br>
	45-60: ${olderAdultP }%</br>
	60+: ${elderlyP }%</br>
	</br>
</div>
</body>
</html>