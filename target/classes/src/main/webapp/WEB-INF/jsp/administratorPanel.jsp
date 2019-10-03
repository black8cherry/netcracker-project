<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="text-center">
<div class="container mt-5">
        <form action="/editAttribute">
            <button class="mt-5 btn btn-success mr-sm-" type="submit">Edit attributes</button>
        </form>
        <form action="${pageContext.request.contextPath}/addFilm">
            <button type="submit" class="mt-5 btn btn-success mr-sm-2">Add new object</button>
        </form>
</div>
</body>
</html>
