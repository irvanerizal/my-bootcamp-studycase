<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Withdraw Page</title>
   </head>
   <body>
      <h1>Withdraw Menu</h1>
      <a href="transaction">Back to transaction</a>
      <br>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "withdraw">
         <td><b>1.</b> Withdraw 10</td>
         <br>
         <td><b>2.</b> Withdraw 50</td>
         <br>
         <td><b>3.</b> Withdraw 100</td>
         <br>
         <td><b>4.</b> Other</td>
         <br>
         Input Withdraw Menu : <input type="text" name="withdrawinput" />
         <br>
         <input type="submit" />
      </form>
   </body>
</html>