<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>user</title>
</head>
<body>

${user.getUsername()}
${user.getRole()}

your favorites movies
<table>
<c:forEach  items="${fav}" var ="fav">
    <tr>
        <td><img src="../img/${fav.object.getFilename()}"/></td>
        <td><a href="/main/${fav.object.getId()}">${fav.object.getName()}</a></td>
        <td><a href="${pageContext.request.contextPath}/main/${id}/removeFavorite">remove from favorite</a></td>
    </tr>
</c:forEach>
</table>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
