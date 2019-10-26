<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>NetFilms</title>
    <style><%@include file="../css/bg.css"%></style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="mx-auto" style="width: 335px;"><h3>Welcome, to your account</h3></div>
<div class="container mb-5">
    <span>Name : ${user.getUsername()}</span><br/>
    <span>Your status : ${user.getRole()}</span>

    <div class="mx-auto" style="width: 160px;">Your favorites movies</div>


</div>

<div class="album py-5 bg-dark" >
    <div class="container">
        <div class="row">
            <c:forEach  items="${fav}" var ="movie">
                <div class="col-md-3">
                    <div class="card mb-3 shadow-sm" style="background-color: #151515">
                        <img class="card-img-top" style="height: 225px; width: 100%; display: block;"
                             src="../img/${movie.filename}">
                        <div class="card-body  mx-auto">
                            <p class="card-text">
                                <a href="/main/${movie.id}">${movie.name}</a>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<%--<table>
<c:forEach  items="${fav}" var ="fav">
    <tr>
        <td><img src="../img/${fav.movie.getFilename()}"/></td>
        <td><a href="/main/${fav.movie.getId()}">${fav.movie.getName()}</a></td>
    </tr>
</c:forEach>
</table>--%>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
