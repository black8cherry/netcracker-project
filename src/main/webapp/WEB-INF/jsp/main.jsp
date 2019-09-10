<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 30.08.2019
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>netfilms</title>
</head>
<body>
<div>
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="sign out"/>
    </form>
</div>

<div>
    <form method="get" class="d-flex" action="${pageContext.request.contextPath}/main">
        <input class="form-control" type="text" name="filter" />
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">find</button>
    </form>
</div>

<div>
    <table>

        <c:forEach  items="${objects}" var ="object">
            <tr>

                <td><img src="img/${object.filename}"/></td>

                <td><a href="/main/${object.id}">${object.name}</a></td>
                <td><a href="${pageContext.request.contextPath}/main/delete/${object.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<a href="${pageContext.request.contextPath}/addFilm" >add film or serial</a>
<a href="${pageContext.request.contextPath}/userList">user list</a>
</body>
</html>
