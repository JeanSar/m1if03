<%--
  Created by IntelliJ IDEA.
  User: coraille
  Date: 09/10/2021
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%String title = request.getParameter("title");%>
<h1 class="header-titre"><%=title%></h1>
<c:if test="${!empty sessionScope.user}">
    <p class="header-user"> Bonjour ${sessionScope.user.nom}</p>
</c:if>
