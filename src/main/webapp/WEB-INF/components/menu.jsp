<%--
  Created by IntelliJ IDEA.
  User: coraille
  Date: 09/10/2021
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String newcontext = request.getContextPath() + "/election"; %>
<aside class="menu">
    <h2>Menu</h2>
    <ul>
        <c:if test="${!empty sessionScope['user']}">

            <li><a href="<%=newcontext%>/vote">Voter</a></li>
            <li><a href="<%=newcontext%>/vote/ballot">Votre vote</a></li>
            <c:if test="${sessionScope['user'].isAdmin()}">
                <li><a href="<%=newcontext%>/listBallots">Liste des votes</a></li>
            </c:if>
            <li><a href="<%=newcontext%>/user/settings">Paramètres</a></li>
            <li><a href="<%=newcontext%>/user/deco">Déconnexion</a></li>
        </c:if>
        <br>
        <li><a href="<%=newcontext%>/resultats">Résultats</a></li>
    </ul>
</aside>
