<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Withdraw Custom Page</title>
   </head>
   <body>
      <h1>Withdraw Custom Menu</h1>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "withdrawcustom">
         Enter amount to withdraw
         <br>
         Input Withdraw Amount : <input type="text" name="custominput" />
         <br>
         <input type="submit" />
      </form>
   </body>
</html>