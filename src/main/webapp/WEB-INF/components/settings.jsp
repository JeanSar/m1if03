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
<%@ page import="java.util.ArrayList" %>


<html>
<head>
    <title>Paramètres</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ressources/css/vote.css">
</head>
<body>
<header>
    <jsp:include page="header.jsp">
        <jsp:param name="title" value="Résultats actuels de l'élection"/>
    </jsp:include>
</header>
<main id="contenu" class="wrapper">

    <jsp:include page="menu.jsp" />

    <article class="contenu">

        <form method="post" action="changeSettings">
            <h2>Modifier mon nom</h2>
            <p>
                <label>
                    Entrez votre nom :
                    <input type="text" name="nom">
                </label>
            </p>
            <p>
                <input type="submit" name="action" value="Changer   ">
            </p>
        </form>
    </article>
</main>
</body>
</html>
