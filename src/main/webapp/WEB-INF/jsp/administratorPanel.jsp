<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
</head>
<body class="" >
<div class="container mt-5 text-center">
    <h3>Choose the action</h3>

    <form action="${pageContext.request.contextPath}/addFilm">
        <button type="submit" class="mt-5 btn btn-success">Add new movie</button>
    </form>

    <form action="/objType">
        <button class="mt-5 btn btn-success " type="submit">Edit movie type</button>
    </form>

    <form action="${pageContext.request.contextPath}/main">
        <button type="submit" class="mt-5 btn btn-success">Return to main page</button>
    </form>
</div>
</body>
</html>
