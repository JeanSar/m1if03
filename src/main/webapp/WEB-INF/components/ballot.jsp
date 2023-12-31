<%--
  Created by IntelliJ IDEA.
  User: Lionel
  Date: 03/10/2021
  Time: 13:14
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@ page errorPage="../error.jsp" --%>
<% String newcontext = request.getContextPath() + "/election"; %>
<c:if test="${sessionScope.user == null && sessionScope.user.login == null}">
    <% response.sendError(403);%>
</c:if>
<html>
<head>
    <title>Ballot</title>
    <meta http-equiv="refresh" content="200;URL='${pageContext.request.contextPath}/election/vote/ballot'" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/css/vote.css">
</head>
<body>
<header>
    <jsp:include page="header.jsp">
        <jsp:param name="title" value="Voter pour qui vous voulez"/>
    </jsp:include>
</header>
<main id="contenu" class="wrapper">

    <jsp:include page="menu.jsp" />
    <article class="contenu">
        <h2>Votre preuve de vote</h2>
        <c:if test="${!empty sessionScope.ballot}">
            <jsp:useBean id="ballot" scope="session" type="fr.univlyon1.m1if.m1if03.classes.Ballot" />
            <form method="post" action="<%=newcontext%>/vote/deleteVote">
                <p class="header-user"> Votre vote:<strong> ${sessionScope['ballot'].getBulletin().getCandidat().getNom()} ${applicationScope['ballot'].getBulletin().getCandidat().getPrenom() }</strong></p>
                <input type="hidden" name="user" value="${sessionScope['user'].getLogin()}">
                <p>
                    <input type="submit" name="action" value="supprimer">
                </p>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.ballot }">
            <p class="header-user"> Vous n'avez votez pour personne!</p>
            <p>
                <a href="<%=newcontext%>/vote" class="button">Allez VOTER!</a>
            </p>
        </c:if>
        <c:if test="${!empty sessionScope['nbLoadVotes']}">
            <jsp:useBean id="nbLoadVotes" scope="session" type="java.lang.Integer" />
            ${sessionScope['nbLoadVotes']} utilisateurs ont déjà votés !!
        </c:if>
    </article>
</main>
</body>
</html>