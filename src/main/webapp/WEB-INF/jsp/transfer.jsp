<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Transfer Page</title>
   </head>
   <body>
      <h1>Transfer Menu</h1>
      <a href="transaction">Back to transaction</a>
      <br>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "transfer">
         Please enter destination account
         <br>
         <input type="text" name="destinationinput" />
         <br>
         <td>Please enter transfer amount</td>
         <br>
         <input type="text" name="transferamount" />
         <br>
         <input type="submit" />
      </form>
   </body>
</html>