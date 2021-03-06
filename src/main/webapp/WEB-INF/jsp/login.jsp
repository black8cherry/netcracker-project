<%@ page import="com.source.project.service.UserService" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
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
    <div class="row justify-content-md-center">
    <form class="form-group mt-xl-5" action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION!=null }">
           <div class="alert alert-danger" role="alert">
                   ${SPRING_SECURITY_LAST_EXCEPTION.message}
           </div>
       </c:if>
        <label class=""> User Name : </label>
        <input class="form-control mb-2" type="text" name="username"/>
        <label> Password: </label>
        <input class="form-control mb-2" type="password" name="password"/>
        <button class="btn btn-lg btn-primary btn-block mb-2 " type="submit">Sign In</button>
        <a href="${pageContext.request.contextPath}/registration">add new user</a>
    </form>
    </div>
</div>
</body>
</html>
