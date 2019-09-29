<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>NetFilms</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

<div class="container">

    <div class="row">

        <div class="col">
            <h3>NetFilms</h3>
        </div>

        <div class="col mx-auto" style="/*width: 400px; */height: 40px;">
            <form class="form-inline mx-auto"   method="get" action="${pageContext.request.contextPath}/main">
                <input class="form-control mx-auto" style="width: 250px;" type="text" name="Search" />
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

    <nav class=" navbar navbar-expand-lg navbar-fixed-top navbar-dark" style="height: 60px">

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav mx-auto" >
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/main">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/main">Films</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/main">Series</a>
                </li>

            </ul>
        </div>

    </nav>


    ${objects.name}<br/>
    <img src="../img/${objects.filename}"/>
    <table>
        <c:forEach items="${fl}" var="fl">
            <tr>
                <td>${fl.label}</td>
                <td>${fl.value}</td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${checkUser==true}">

        <c:if test="${checkFilm==false}">
            <a href="${pageContext.request.contextPath}/main/${id}/addFavorite">add to favorite</a>
        </c:if>

        <c:if test="${checkFilm==true}">
            <a href="${pageContext.request.contextPath}/main/${id}/removeFavorite">remove from favorite</a>
        </c:if>
    </c:if>

    <c:if test="${role=='[ADMIN]'}">
        <a href="${pageContext.request.contextPath}/main/${id}/edit">edit object</a>
        <a href="${pageContext.request.contextPath}/main/${id}/editObjectAttributes">edit attributes</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/main" >link to main</a>

</div>
</body>
</html>
