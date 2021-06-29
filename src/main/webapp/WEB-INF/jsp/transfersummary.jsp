<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Fund Transfer Summary</title>
   </head>
   <body>
      <h1>Fund Transfer Summary</h1>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "transfersummary">
        Destination Accout : ${destination}
         <br>
         Transfer Amount : ${amount}
         <br>
         Reference Number : ${referencenumber}
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