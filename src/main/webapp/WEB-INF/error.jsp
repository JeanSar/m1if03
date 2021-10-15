<%--
  Created by IntelliJ IDEA.
  User: coraille
  Date: 10/10/2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error <%= response.getStatus()%></title>H
    </head>
    <body>
        Error <%= response.getStatus()%> : Cannot access !!
    </body>
</html>