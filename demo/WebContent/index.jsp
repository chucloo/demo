<%@ page import="demo.Calculator" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
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

<br>

 <h1>File Upload</h1>
    <form method="post" action="S3FileUpload"
        enctype="multipart/form-data">
        Select file to upload: <input type="file" name="file" size="60" /><br />
        <br /> <input type="submit" value="Upload" />
  </form>


</body>
</html>