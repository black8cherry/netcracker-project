<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link href="../css/main.css" rel="stylesheet" type="text/css">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>netfilms</title>
</head>
<body>
<div class="topnav">
    <a class="active" href="/main">Home</a>
    <a href="#about">About</a>

    <form method="get" action="${pageContext.request.contextPath}/main">
        <input type="text" name="filter" />
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">find</button>
    </form>

    <c:choose>
        <c:when test="${checkUser==true}" >
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="sign out"/>
            </form>
            <form action="${pageContext.request.contextPath}/user/${userAcc.id}" method="get">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="my account"/>
            </form>
            <%--<a href="${pageContext.request.contextPath}/user/${userAcc.id}">user list</a>--%>
        </c:when>
        <c:when test="${checkUser==false}">
            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="sign in">
            </form>
        </c:when>
    </c:choose>
</div>

<div>
    <table>

        <c:forEach  items="${objects}" var ="object">
            <tr>

                <td><img src="img/${object.filename}"/></td>

                <td><a href="/main/${object.id}">${object.name}</a></td>
                <c:if test="${role=='[ADMIN]'}">
                <td><a href="${pageContext.request.contextPath}/main/delete/${object.id}">delete</a></td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>

<c:if test="${role=='[ADMIN]'}">
    <a href="${pageContext.request.contextPath}/addFilm" >add film or serial</a>
    <a href="${pageContext.request.contextPath}/editAttribute">edit attributes</a>

</c:if>

</body>
</html>
