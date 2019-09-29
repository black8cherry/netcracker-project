<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

<%--<div>
    <form action="${pageContext.request.contextPath}/main/${id}/editObjectAttributes" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="label" placeholder="label"/>
        <button type="submit">add</button>
    </form>
</div>--%>

<table>
    <c:forEach items="${attributes}" var="att">
        <tr>
            <td>${att.attribute.getLabel()}</td>
            <td><a href="${pageContext.request.contextPath}/main/${id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
        </tr>
    </c:forEach>
</table>

<table>
    <c:forEach items="${mAttributes}" var="att">
        <tr>
            <td>${att.getLabel()}</td>
            <td><a href="${pageContext.request.contextPath}/main/${id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
        </tr>
    </c:forEach>
</table>


<a href="${pageContext.request.contextPath}/main">to main</a>
</body>
</html>
