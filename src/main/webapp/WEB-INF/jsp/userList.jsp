<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>user</title>
    <style><%@include file="../css/bg.css"%></style>
</head>
<body>

${user.getUsername()}
${user.getRole()}

your favorites movies
<table>
<c:forEach  items="${fav}" var ="fav">
    <tr>
        <td><img src="../img/${fav.movie.getFilename()}"/></td>
        <td><a href="/main/${fav.movie.getId()}">${fav.movie.getName()}</a></td>
    </tr>
</c:forEach>
</table>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
