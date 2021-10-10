<%--
  Created by IntelliJ IDEA.
  User: Lionel
  Date: 03/10/2021
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="WEB-INF/error.jsp" %>

<c:if test="${sessionScope.user == null && sessionScope.user.login == null}">
    <% response.sendError(403);%>
</c:if>
<html>
<head>
    <title>Ballot</title>
    <link rel="stylesheet" type="text/css" href="vote.css">
</head>
<body>
<header>
    <jsp:include page="WEB-INF/components/header.jsp">
        <jsp:param name="title" value="Voter pour qui vous voulez"/>
    </jsp:include>
</header>
<main id="contenu" class="wrapper">

    <jsp:include page="WEB-INF/components/menu.jsp" />
    <article class="contenu">

        <%-- jsp:useBean id="votes" scope="request" class="java.util.HashMap" /--%>
        <h2>Votre preuve de vote</h2>
        <c:if test="${ !empty sessionScope.vote}">
            <form method="post" action="deleteVote">
            <p>Votre vote:<strong> ${sessionScope.vote.nom}</strong> </p>
            <input name="nomVote" id="nomVote" type="hidden" value="${sessionScope.vote.nom}">
            <p>
                <input type="submit" name="action" value="supprimer">
            </p>
        </form>
        </c:if>

        <c:if test="${empty sessionScope.vote}">
            <p class="header-user"> Vous n'avez votez pour personne!</p>
            <p>
                <a href="vote.jsp" class="button">Allez VOTER!</a>
            </p>
        </c:if>

    </article>
</main>
</body>
</html>
