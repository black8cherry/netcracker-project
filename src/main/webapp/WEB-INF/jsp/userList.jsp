<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 01.09.2019
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user</title>
</head>
<body>
<table>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>${user.role}</td>
           <%-- <td><a href="${pageContext.request.contextPath}/user/{{id}}">edit</a></td>--%>
        </tr>

    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
