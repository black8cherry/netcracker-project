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

        <div class="col" >
            <div class=" float-right ">
                <c:if test="${checkUser==true}">
                    <div class="form-inline">
                        <form class="" action="${pageContext.request.contextPath}/logout" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-dark mr-sm-2" type="submit">sign out</button>
                        </form>

                        <form class=" " action="${pageContext.request.contextPath}/user/${userAcc.id}" method="get">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-dark mr-sm-2" type="submit">my account</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${checkUser==false}">

                    <form class="form-inline "  action="${pageContext.request.contextPath}/login" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input class="btn btn-dark mr-sm-2" type="submit" value="sign in">
                    </form>
                </c:if>
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


    <div class="row mt-5">
        <div class="col-lg-5 " >
            <img class="float-right" style="height: 225px; width: 225px; display: block;"
                    src="../img/${objects.filename}"/>


        </div>

        <div class="col float-left">
            <h5 class="ml-5">${objects.name}</h5>
            <table class="mt-4">
                <c:forEach items="${fl}" var="fl">
                <tr>
                    <td>${fl.label}</td>
                    <td>${fl.value}</td>
                </tr>
                </c:forEach>
            </table>

            <c:if test="${checkUser==true}">

                <c:if test="${checkFilm==false}">
                    <form action="${pageContext.request.contextPath}/main/${id}/addFavorite">
                        <button class="btn btn-dark mt-3" type="submit">add to favorite</button>
                    </form>
                </c:if>

                <c:if test="${checkFilm==true}">
                    <form action="${pageContext.request.contextPath}/main/${id}/removeFavorite">
                        <button class="btn btn-dark mt-3" type="submit">remove from favorite</button>
                    </form>

                </c:if>
            </c:if>





        </div>

        <div class="col col-lg-2">
        <c:if test="${role=='[ADMIN]'}">
            <form action="${pageContext.request.contextPath}/main/${id}/edit">
                <button class="btn btn-dark mt-3" type="submit">edit object</button>
            </form>
            <form action="${pageContext.request.contextPath}/main/${id}/editObjectAttributes">
                <button class="btn btn-dark mt-3" type="submit">edit attributes</button>
            </form>

        </c:if>
        </div>

    </div>

    <div class="row">
        <div class="col">
            <c:if test="${checkUser==true}">
                <form class="mt-4 form-inline justify-content-center" action="/main/${id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input class="form-control mr-2" style="width: 300px; height: 70px;" type="text" name="message" value="Make your review">
                    <button class="btn btn-dark mb-4" type="submit">Add new review</button>
                </form>
            </c:if>

            <c:forEach items="${messages}" var="mes">
                <tr>
                    <td>${mes.user.getUsername()} : </td>
                    <td>${mes.message}</td> <hr>
                    <c:if test="${userAcc.id==mes.user.getId() || role=='[ADMIN]'}">
                        <td>
                            <form action="/deleteMessage/${id}/${mes.id}">
                                <button class="btn btn-dark" type="submit">delete</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </div>
    </div>

</div>
</body>
</html>
