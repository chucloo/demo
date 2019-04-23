<%@ page import="demo.Calculator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jenkin Pipeline</title>
</head>
<body>
CI/CD page 3 ... With Docker

<br>

<% Calculator calculator = new Calculator(); %>

    Addition:
    <%= calculator.addition(50, 10) %>


</body>
</html>