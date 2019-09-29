<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>addFilm</title>
</head>
<body>

<div>
    <form action="${pageContext.request.contextPath}/addFilm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="name" placeholder="input the name of object"/>
        <input type="text" name="type" placeholder="input the type: 1. film 2. serial"/>
        <input type="file" name="file"/>
        <button type="submit">add</button>
    </form>
</div>

<table>
    <c:forEach  items="${objects}" var = "object">
        <tr>
            <td>
            <div>
                <img src="img/${object.filename}"/>
            </div>
            </td>
            <td>${object.id}</td>
            <td>${object.name}</td>
            <td>${object.type.type}</td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/main">to main</a>
</body>
</html>