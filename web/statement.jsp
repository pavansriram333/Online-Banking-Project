<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/css2?family=Lato:wght@400;900&display=swap" rel="stylesheet">
   
    <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href="style2.css" rel="stylesheet" type="text/css">
</head>
    <div class="wrapper">
<ul class="nav-area">
<li><a href="welcome.html">Home</a></li>
<li><a href="accsummary.jsp">Account Summary</a></li>
<li><a href="fundtransfer.html">Transfer</a></li>
<li><a href="statement.jsp">Statement</a></li>
<li><a href="change.html">Change of details</a></li>
<li><a href="index.html">Logout</a></li>
</ul>
</div>

<body>
  
  <div class="welcome-text">
    <div  class="container" style="width:500px">
  <div class="kuchnai"> Account Details</div>
  <br><br>
  
  <form action="statementservlet" method="get">
       <button type="submit" class="btn btn-success">Click for A/C statement</button>
  </form>
  </div>
</div>
</body>
   


</html>