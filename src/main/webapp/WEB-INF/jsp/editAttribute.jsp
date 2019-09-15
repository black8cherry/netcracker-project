<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>editAttribute</title>
</head>
<body>

<div>
    <form action="${pageContext.request.contextPath}/main/${id}/edit/editAttribute" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="label" placeholder="label"/>
        <button type="submit">add</button>
    </form>
</div>

<table>
    <c:forEach  items="${attributes}" var = "att">
        <tr>
            <td>${att.label}</td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/main">to main</a>
</body>
</html>