<%--
  Created by IntelliJ IDEA.
  User: coraille
  Date: 09/10/2021
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String electioncontext = request.getContextPath() + "/election"; %>
<% String usercontext = request.getContextPath() + "/user"; %>
<aside class="menu">
    <h2>Menu</h2>
    <ul>
        <c:if test="${!empty sessionScope['user']}">

            <li><a href="<%=electioncontext%>/vote">Voter</a></li>
            <li><a href="<%=electioncontext%>/vote/ballot">Votre vote</a></li>
            <c:if test="${sessionScope['user'].isAdmin()}">
                <li><a href="<%=usercontext%>/listBallots">Liste des votes</a></li>
            </c:if>
            <li><a href="<%=usercontext%>/settings">Paramètres</a></li>
            <li><a href="<%=usercontext%>/deco">Déconnexion</a></li>
        </c:if>
        <br>
        <li><a href="<%=electioncontext%>/resultats">Résultats</a></li>
    </ul>
</aside>
