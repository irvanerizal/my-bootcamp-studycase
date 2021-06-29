<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Withdraw Summary</title>
   </head>
   <body>
      <h1>Withdraw Summary</h1>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "withdrawsummary">
         Summary
         <br>
         Date : ${date}
         <br>
         Withdraw : ${amount}
         <br>
         Balance : ${balance}
         <br>
         1. Transaction
         <br>
         2. Exit
         <br>
         Choose Option[2] : <input type="text" name="summaryinput" />
         <br>
         <input type="submit" />
      </form>
   </body>
</html>