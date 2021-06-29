<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<title>Welcome Page</title>
<!-- Access the bootstrap Css like this,
        Spring boot will handle the resource mapping automcatically -->
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>

<body>
<h1>Welcome Page</h1>
    <font color="red">${errorMessage}</font>
    <form method="post" action = "welcome">
        Account Number :
        <br>
        <input type="text" name="account" />
        <br>
        PIN :
        <br>
        <input type="password" name="pin" />
        <br>
        <input type="submit" />
    </form>
</body>

</html>