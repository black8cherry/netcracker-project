<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>registration</title>
    <style><%@include file="../css/bg.css"%></style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body class="text-center">
<div class="container">
    <div class="row justify-content-md-center ">
        <form class="form-group mt-xl-5" action="${pageContext.request.contextPath}/registration" method="post">
            <label class="mt-5"> User Name : </label>
            <input class="form-control mb-2" type="text" name="username"/>
            <label> Password: </label>
            <input class="form-control mb-2" type="password" name="password"/>
            <input class="mb-2" type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-lg btn-primary btn-block mb-2 " type="submit">Register</button>
        </form>

    </div>
</div>
</body>
</html>
