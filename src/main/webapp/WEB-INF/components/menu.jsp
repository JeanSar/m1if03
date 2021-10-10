<%--
  Created by IntelliJ IDEA.
  User: coraille
  Date: 09/10/2021
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="menu">
    <h2>Menu</h2>
    <ul>
        <c:if test="${!empty sessionScope.user}">
            <li><a href="vote.jsp">Voter</a></li>
            <li><a href="ballot.jsp">Votre vote</a></li>
            <li><a href="deco">Déconnexion</a></li>
        </c:if>
        <br>
        <li><a href="resultats.jsp">Résultats</a></li>
    </ul>
</aside>
