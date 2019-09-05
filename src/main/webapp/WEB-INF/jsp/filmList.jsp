<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 01.09.2019
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>filmList</title>
</head>
<body>
<div>
    <img src="../img/${objects.filename}"/>
    ${objects.name}
</div>

<div>
    <table>
        <c:forEach items="${fl}" var="fl">
            <tr>
                <td>${fl.label}</td>
                <td>${fl.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<a href="${pageContext.request.contextPath}/main/${id}/edit">edit</a>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
