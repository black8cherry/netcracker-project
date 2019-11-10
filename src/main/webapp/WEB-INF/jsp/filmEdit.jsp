<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/filmEdit.css"%></style>
</head>
<body class="text-center">
<div class="container">
    <div class="mx-auto mt-5">
        <form class="mt-5" action="${pageContext.request.contextPath}/main/${id}/edit" method="post" >
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input class="mb-2" type="text" name="objectName" value="${objects.name}"/>
            <input class="mb-2" type="hidden" name="objectId" value="${objects.id}"/>
            <br>
            <c:forEach items="${attributeValue}" var="attributeValue">
                <c:forEach items="${objectAttributes}" var="att">
                    <c:if test="${att.label==attributeValue.getKey()}">
                        <c:set var="type" value="${att.labelType}"/>
                    </c:if>
                </c:forEach>
                    ${attributeValue.getKey()}
                <input class="mb-2" type="hidden" name="label" value="${attributeValue.getKey()}"/>
                <input class="mb-2"
                       type="text"
                       id="values"
                       name="value"
                       pattern="${type=='numerical' ? '[0-9]+' : '[A-Za-z0-9]+'}"
                       value="${attributeValue.getValue()}"
                />
                <span class="error" aria-live="polite"></span>
                <br>
            </c:forEach>


            <button class="btn btn-dark mr-sm-2" type="submit">Save changes</button>
        </form>

        <form action="${pageContext.request.contextPath}/main" class="inline">
            <button class="btn btn-dark mr-sm-2" type="submit">Main page</button>
        </form>
    </div>
</div>

<script src="../js/filmEdit.js"></script>

</body>
</html>
