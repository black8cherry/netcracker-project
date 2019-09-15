<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Edit page
<form action="${pageContext.request.contextPath}/main/${id}" method="post" modelAttribute="fl">
    <input type="text" name="objectName" value="${objects.name}"/>
    <input type="text" name="objectId" value="${objects.id}"/>

        <c:forEach items="${fl}" var="fl">
                <span>${fl.label}</span>
                <input path="fl.label" type="hidden" name="label" value="${fl.label}"/>
                <input path="fl.value" type="text" name="value" value="${fl.value}"/>
        </c:forEach>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">save</button>
</form>

<a href="/main/${id}/edit/editAttribute">edit attributes</a>

</body>
</html>
