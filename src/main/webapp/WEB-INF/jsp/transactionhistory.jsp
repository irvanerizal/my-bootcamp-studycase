<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
   <style>
      table {

      border-collapse: collapse;
      width: 100%;
      }
      td, th {
      border: 1px solid #dddddd;
      text-align: left;
      padding: 8px;
      }
      tr:nth-child(even) {
      background-color: #dddddd;
      }
   </style>
   <head>
      <title>Transaction History Page</title>
   </head>
   <body>
      <h1>User Transaction History</h1>
      <a href="transaction">Back to transaction</a>
      <form method="post" action = "transferconfirmation">
         <h3>User Latest Balance : ${balance}</h3>
         <h3>User Transaction Histories :</h3>
         <table>
            <c:forEach var="trx" items="${transaction}">
               <tr>
                  <td>
                     Transaction Type : ${trx.type}
                     <br>
                     Created By : ${trx.createdBy}
                     <br>
                     Date Time : ${trx.dateTime}
                     <br>
                     <c:choose>
                        <c:when test="${trx.type=='WITHDRAW'}">
                           Amount : ${trx.amount}
                  </td>
                  <br>
                  </c:when>
                  <c:otherwise>
                  Sender Acc : ${trx.originAccountNo}
                  <br>
                  Receiver Acc : ${trx.destinationAccountNo}
                  <br>
                  Refrence Number : ${trx.refrenceNumber}
                  <br>
                  Transfer Amount : ${trx.amount}
                  </td>
                  <br>
                  </c:otherwise>
                  </c:choose>
               </tr>
            </c:forEach>
         </table>
      </form>
   </body>
</html>