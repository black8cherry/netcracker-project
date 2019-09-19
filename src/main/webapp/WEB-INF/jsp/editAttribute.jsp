<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>editAttribute</title>
</head>
<body>

<div>
    <form action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="label" placeholder="label"/>
        <input type="text" name="choose" placeholder="film/series"/>
        <button type="submit">add</button>
    </form>
</div>

<table>
    <c:forEach  items="${attributes}" var="att">
        <tr>
            <td>${att.label}</td>
            <td><a href="${pageContext.request.contextPath}/editAttribute/delete/${att.label}">delete</a></td>
        </tr>
    </c:forEach>
</table>

<table>
    <c:forEach items="${typeAttributes}" var="typeAtt">
        <tr>
            <td>${typeAtt.type.getType()}</td>
            <td>${typeAtt.attribute.getLabel()}</td>
            <td><a href="${pageContext.request.contextPath}/editAttribute/deleteTypeAtt/${typeAtt.id}">delete</a> </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/main">to main</a>
</body>
</html>