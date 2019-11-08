<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>registration</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/registration.css"%></style>
</head>
<body class="text-center">
<div class="container">
    <div class="row justify-content-md-center ">
        <form class="form-group mt-xl-5" action="${pageContext.request.contextPath}/registration" method="post">
            <label class="mt-5"> User Name : </label>
            <input class="form-control mb-2 ${ textError.username!=null ? 'is-invalid' : ''}" type="text" name="username"/>
            <c:if test="${textError.username!=null}">
                <div class="invalid-feedback">
                ${textError.username} <br>
                </div>
            </c:if>
            <label> Password: </label>
            <input class="form-control mb-2 ${ textError.password!=null ? 'is-invalid' : ''}" type="password" name="password"/>
            <c:if test="${textError.password!=null}">
                <div class="invalid-feedback">
                ${textError.password} <br>
                </div>
            </c:if>
            <input class="mb-2 mt-2" type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-lg btn-primary btn-block mb-2 " type="submit">Register</button>
            <span style="color: red;">${message}</span>
        </form>

    </div>
</div>
</body>
</html>
