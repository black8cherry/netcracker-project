<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 04.09.2019
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Edit page
<form action="${pageContext.request.contextPath}/main/${id}" method="post">
    <input type="text" name="objectName" value="${objects.name}"/>
    <input type="text" name="objectId" value="${objects.id}"/>

        <c:forEach items="${val}" var="v">

                <input type="text" name="val" value="${v.value}">
        </c:forEach>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">save</button>
</form>



</body>
</html>
