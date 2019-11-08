<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>NetFilms</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
</head>
<body >
<div class="container">
    <div class="row mt-3">

        <div class="col">
            <h3 style="color: aliceblue">NetFilms</h3>
        </div>

        <div class="col mx-auto" style="/*width: 400px; */height: 40px;">
            <form class="form-inline mx-auto"   method="get" action="${pageContext.request.contextPath}/main">
            <input  class="form-control mx-auto" style="width: 250px;" type="text" name="filter" />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-success" type="submit">Search</button>
            </form>
        </div>

        <div class="col" >
            <div class=" float-right ">
                <c:choose>
                <c:when  test="${checkUser==true}" >
                <div class="form-inline">
                    <form class="" action="${pageContext.request.contextPath}/logout" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input class="btn btn-dark mr-sm-2" type="submit" value="sign out"/>
                    </form>

                    <form class=" " action="${pageContext.request.contextPath}/user/${userAcc.id}" method="get">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input class="btn btn-dark mr-sm-2" type="submit" value="my account"/>
                    </form>
                </div>
                </c:when>

                <c:when test="${checkUser==false}">
                    <form class="form-inline "  action="${pageContext.request.contextPath}/login" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input class="btn btn-dark mr-sm-2" type="submit" value="sign in">
                    </form>

                </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<nav class=" navbar navbar-expand-lg navbar-fixed-top navbar-dark" style="height: 60px">

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

    <ul class="navbar-nav mx-auto" >
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/main">Home</a>
        </li>
        <c:if test="${role=='[ADMIN]'}">
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/main/administratorPanel">Administrator panel</a>
        </li>
        </c:if>
    </ul>
    </div>

</nav>


<div class="album py-5 bg-dark" >
    <div class="container">
        <div class="row">
            <c:forEach  items="${movie}" var ="movie">
            <div class="col-md-3">
                <div class="card mb-3 shadow-sm" style="background-color: #151515">
                    <img class="card-img-top" style="height: 225px; width: 100%; display: block;"
                         src="img/${movie.filename}">
                    <div class="card-body  mx-auto">
                        <p class="card-text">
                            <a href="/main/${movie.id}">${movie.name}</a>
                            <c:if test="${role=='[ADMIN]'}">
                                <form class="form-inline" action="${pageContext.request.contextPath}/main/delete/${movie.id}">
                                    <button type="submit" class="btn btn-dark ">Delete</button>
                                </form>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
</div>




</body>
</html>
