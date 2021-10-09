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
    <p class="header-user"> Bonjour ${sessionScope.user.nom}</p>
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
        <h2>Voter pour qui vous voulez</h2>
        <%
            Map<String, Integer> votes = new HashMap<>();
            for (String nomCandidat : ((Map<String, Candidat>) application.getAttribute("candidats")).keySet()) {
                votes.put(nomCandidat, 0);
            }

        %>
        <form method="post" action="vote">
            Sélectionnez un candidat :
            <select name="candidats" id="candidats">
                <c:forEach items="<%= votes.keySet()%>" var="nomCandidat">
                    <option id="candidats" value="${nomCandidat}"><c:out value="${nomCandidat}"/></option>
                </c:forEach>
               
            </select>
            <p>
                <input type="submit" name="action" value="Envoyer votre vote">
            </p>
        </form>
        
    </article>
</main>
</body>
</html>
