<%--
  Created by IntelliJ IDEA.
  User: Lionel
  Date: 03/10/2021
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vote</title>
    <link rel="stylesheet" type="text/css" href="vote.css">
</head>
<body>
<header>
    <jsp:include page="WEB-INF/components/header.jsp">
        <jsp:param name="title" value="Résultats actuels de l'élection"/>
    </jsp:include>
</header>
<main id="contenu" class="wrapper">

    <jsp:include page="WEB-INF/components/menu.jsp" />
    <article class="contenu">
        <h2>Voici le résultat courant de l'élection</h2>
        <jsp:useBean id="votes" scope="request" class="java.util.HashMap"/>
        <ul>
            <c:forEach items="<%= votes.keySet()%>" var="nomCandidat">
                <li><c:out value="${nomCandidat}"/> : <%= votes.get(pageContext.getAttribute("nomCandidat")) %> vote(s)</li>
            </c:forEach>
        </ul>
    </article>
</main>
</body>
</html>
