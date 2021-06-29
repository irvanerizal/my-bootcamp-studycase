<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
   <head>
      <title>Transfer Confirmation</title>
   </head>
   <body>
      <h1>Transfer Confirmation</h1>
      <font color="red">${errorMessage}</font>
      <form method="post" action = "transferconfirmation">
         Destination Accout :
         <br>
         <input type="text" readonly="readonly" name=destination value=${destination}></input>
         <br>
         Transfer Amount :
         <br>
         <input type="text" readonly="readonly" name=amount value=${amount}></input>
         <br>
         Reference Number :
         <br>
         <input type="text" readonly="readonly" name=referencenumber value=${referencenumber}></input>
         <br>
         <br>
         <b>1.</b> Confirm Trx
         <br>
         <b>2.</b> Cancel Trx
         <br>
         Choose Option[2] : <input type="text" name="confirminput" />
         <br>
         <input type="submit" />
      </form>
   </body>
</html>
