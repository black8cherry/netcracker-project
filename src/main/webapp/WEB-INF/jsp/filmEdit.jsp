<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
</head>
<body class="text-center">
<div class="container">
    <div class="mx-auto mt-5">
        <form class="mt-5" action="${pageContext.request.contextPath}/main/${id}/edit" method="post" modelAttribute="fl">
            <input class="mb-2" type="text" name="objectName" value="${movie.name}"/>
            <input class="mb-2" type="text" name="objectId" value="${movie.id}"/>
            <br>
            <c:forEach items="${fl}" var="fl">
                ${fl.label}
                <input class="mb-2" path="fl.label" type="hidden" name="label" value="${fl.label}"/>
                <input class="mb-2" path="fl.value" type="text" name="value" value="${fl.value}"/>
                <br>
            </c:forEach>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <form>
                <button class="btn btn-dark mr-sm-2" type="submit">Save changes</button>
            </form>

            <form action="${pageContext.request.contextPath}/main" class="inline">
                <button class="btn btn-dark mr-sm-2" type="submit">Main page</button>
            </form>

        </form>
    </div>
</div>

</body>
</html>
