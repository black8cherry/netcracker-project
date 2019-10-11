<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class="text-center">
<div class="container ">
    <div class="mx-auto" style="width: 200px;">

        <form action="${pageContext.request.contextPath}/main" >
            <button class="btn btn-dark mt-2" type="submit">Return to main page</button>
        </form>

        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/objType" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input class="form-control mt-4 mr-sm-2" type="text" name="type"/>
            <button class="btn btn-success mt-4 ml-4" type="submit">Add object type</button>
        </form>

        <h3 >Object types</h3>
        <table class="mt-2 ml-5">
            <c:forEach items="${objType}" var="objT">
                <tr>
                    <td>${objT.getType()}</td>
                    <td><a href="${pageContext.request.contextPath}/delObjType/${objT.id}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
