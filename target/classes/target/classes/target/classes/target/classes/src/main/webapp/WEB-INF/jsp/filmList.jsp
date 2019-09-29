<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>filmList</title>
</head>
<body>

<div>
    ${objects.name}<br/>
    <img src="../img/${objects.filename}"/>
    <table>
        <c:forEach items="${fl}" var="fl">
            <tr>
                <td>${fl.label}</td>
                <td>${fl.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<c:if test="${checkUser==true}">
    <a href="${pageContext.request.contextPath}/main/${id}/addFavorite">add to favorite</a>
</c:if>

<c:if test="${role=='[ADMIN]'}">
    <a href="${pageContext.request.contextPath}/main/${id}/edit">edit object</a>
    <a href="${pageContext.request.contextPath}/main/${id}/editObjectAttributes">edit attributes</a>
</c:if>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
