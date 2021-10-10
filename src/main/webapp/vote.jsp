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
<%@ page import="fr.univlyon1.m1if.m1if03.classes.Candidat" %>
<%@ page errorPage="WEB-INF/error.jsp" %>

<c:if test="${sessionScope.user == null && sessionScope.user.login == null}">
    <% response.sendError(403);%>
</c:if>
<html>
<head>
    <title>Vote</title>
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
