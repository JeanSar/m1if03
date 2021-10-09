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


<html>
<head>
    <title>Vote</title>
    <link rel="stylesheet" type="text/css" href="vote.css">
</head>
<body>
<header>
    <c:if test="${!empty sessionScope.user}">
        <p class="header-user"> Bonjour ${sessionScope.user.nom}</p>
    </c:if>
    <h1 class="header-titre">Résultats actuels de l'élection</h1>
</header>
<main id="contenu" class="wrapper">
    <aside class="menu">
        <h2>Menu</h2>
        <ul>
            <c:if test="${!empty sessionScope.user}">
                <li><a href="vote.jsp">Voter</a></li>
                <li><a href="ballot.jsp">Votre vote</a></li>
            </c:if>
            <li><a href="resultats.jsp">Résultats</a></li>
            <c:if test="${!empty sessionScope.user}">
                <li><a href="deco">Déconnexion</a></li>
            </c:if>
        </ul>
    </aside>
    <article class="contenu">
        <h2>Voici le résultat courant de l'élection</h2>
        <%-- jsp:useBean id="votes" scope="request" class="java.util.HashMap" /--%>
        <%
            Map<String, Integer> votes = new HashMap<>();
            for (String nomCandidat : ((Map<String, Candidat>) application.getAttribute("candidats")).keySet()) {
                votes.put(nomCandidat, 0);
            }

        %>

        <ul>
            <c:forEach items="<%= votes.keySet()%>" var="nomCandidat">
                <li><c:out value="${nomCandidat}"/> : <%= votes.get((String)pageContext.getAttribute("nomCandidat")) %> vote(s)</li>
            </c:forEach>
        </ul>
    </article>
</main>
</body>
</html>
