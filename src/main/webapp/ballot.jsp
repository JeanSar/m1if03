<%--
  Created by IntelliJ IDEA.
  User: Lionel
  Date: 03/10/2021
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Bulletin" %>
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Candidat" %>
<%@ page import="java.util.List" %>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="index.html"/>
</c:if>
<html>
<head>
    <title>Vote</title>
    <link rel="stylesheet" type="text/css" href="vote.css">
</head>
<body>
<header>
    <c:if test="${sessionScope.user != null}">
        <p class="header-user"> Bonjour ${sessionScope.user.nom}</p>
    </c:if>
    <h1 class="header-titre">Voter pour qui vous voulez</h1>
</header>
<main id="contenu" class="wrapper">
    <aside class="menu">
        <h2>Menu</h2>
        <ul>
            <li><a href="vote.jsp">Voter</a></li>
            <li><a href="ballot.jsp">Votre vote</a></li>
            <li><a href="resultats.jsp">Résultats</a></li>
            <li><a href="deco">Déconnexion</a></li>
        </ul>
    </aside>
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

        <c:if test="${empty sessionScope.vote || sessionScope.vote == null}">
            <p class="header-user"> Vous n'avez votez pour personne!</p>
            <p>
                <a href="vote.jsp" class="button">Allez VOTER!</a>
            </p>
        </c:if>

    </article>
</main>
</body>
</html>
