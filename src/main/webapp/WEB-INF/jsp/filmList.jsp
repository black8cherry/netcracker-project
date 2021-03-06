<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>NetFilms</title>
    <style><%@include file="../css/filmList.css"%></style>
    <style><%@include file="../css/bg.css"%></style>
    <script><%@include file="../js/filmList.js"%></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body style="background-color: #151515;">

<div class="container">

    <div class="row bar mt-2">

        <div class="col">
            <h3>NetFilms</h3>
        </div>

        <div class="col" >
            <div class=" float-right ">
                <c:if test="${checkUser==true}">

                    <div class="form-inline">
                        <div class="mb-3 mr-3">
                            <span>${userAccount.username}</span>
                        </div>
                        <form class="" action="${pageContext.request.contextPath}/logout" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-dark mr-sm-2" type="submit">sign out</button>
                        </form>

                        <form class=" " action="${pageContext.request.contextPath}/user/${userAccount.id}" method="get">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-dark mr-sm-2" type="submit">my account</button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${checkUser==false}">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-dark mr-sm-2" type="submit">Sign in</a>

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
                 src="../img/${image.value==null ? 'no-image.jpg' : image.value}"/>
        </div>

        <div class="col float-left">
            <h5 class="ml-5">${movie.name}</h5>
            <table class="mt-4">
                <c:forEach items="${movieAttributes}" var="attributeValue">
                    <c:forEach items="${tmpMovieAttributes}" var="att">
                        <c:if test="${att.label==attributeValue.getKey()}">
                            <c:set var="type" value="${att.labelType}"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${type!='image'}">
                        <tr>
                            <td><span>${attributeValue.getKey()} : </span></td>
                            <td><span>${attributeValue.getValue()}</span></td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
                <c:forEach items="${movieAttributes}" var="attributeValue">
                    <c:forEach items="${tmpMovieAttributes}" var="att">
                        <c:if test="${att.label==attributeValue.getKey()}">
                            <c:set var="type" value="${att.labelType}"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${type=='image'}">
                        <img class="mt-2" style="height: 105px; width: 105px; display: block;"
                             src="../img/${attributeValue.getValue().isEmpty()||attributeValue.getValue()==null ? 'no-image.jpg' : attributeValue.getValue()}"/>
                            </c:if>
                </c:forEach>
            <br/>
            <c:if test="${checkRatingType==true}">
                <span>Rate of this movie :</span>
                <c:if test="${rate == '0'}">
                    <span>No one has rated this movie yet</span>
                </c:if>
                <c:if test="${rate != '0'}">
                    <span>${rate}</span>
                </c:if>
                <br/>
            </c:if>
            <c:if test="${checkUser==true}">
                <c:if test="${checkRatingType==true}">
                    <c:if test="${checkRate==true}">
                        <span>You can rate this movie again</span>
                    </c:if>

                    <c:if test="${checkRate==false}">
                        <span>Rate the movie</span>
                    </c:if>
                    <br/>
                    <form action="/rate/${movie.id}/${userAccount.id}">
                    <fieldset class="rating">
                        <input type="radio" id="star5" name="rating" value="5" /><label class = "full" for="star5" title="Awesome - 5 stars"></label>
                        <input type="radio" id="star4" name="rating" value="4" /><label class = "full" for="star4" title="Pretty good - 4 stars"></label>
                        <input type="radio" id="star3" name="rating" value="3" /><label class = "full" for="star3" title="Meh - 3 stars"></label>
                        <input type="radio" id="star2" name="rating" value="2" /><label class = "full" for="star2" title="Kinda bad - 2 stars"></label>
                        <input type="radio" id="star1" name="rating" value="1" /><label class = "full" for="star1" title="Sucks big time - 1 star"></label>
                    </fieldset>
                        <button class="btn btn-dark" type="submit">rate</button>
                    </form>
                </c:if>
                <c:if test="${checkFavoriteType==true}">
                    <c:if test="${checkFilm==false}">
                        <form action="${pageContext.request.contextPath}/main/${id}/${userAccount.id}/addFavorite">
                            <button class="btn btn-dark mt-3" type="submit">add to favorite</button>
                        </form>
                    </c:if>

                    <c:if test="${checkFilm==true}">
                        <form action="${pageContext.request.contextPath}/main/${id}/${userAccount.id}/removeFavorite">
                            <button class="btn btn-dark mt-3" type="submit">remove from favorite</button>
                        </form>
                    </c:if>
                </c:if>
                <c:if test="${checkMessageType==true}">
                    <a href="/main/messages/${id}" class="btn btn-dark"  >Add review</a>
                </c:if>
            </c:if>





        </div>

        <div class="col col-lg-2">
        <c:if test="${role=='[ADMIN]'}">
            <form action="${pageContext.request.contextPath}/main/${id}/edit">
                <button class="btn btn-dark mt-3" type="submit">edit movie</button>
            </form>


        </c:if>
        </div>

    </div>
    <div class="row">
        <div class="col">
            <c:if test="${checkMessageType==true}">
                <c:forEach items="${userMessages}" var="mes">
                    <div class="mes mx-auto mt-4 pt-1" style="width: 600px">
                    <tr>
                        <div class="ml-2">
                        <c:forEach items="${mes}" var="attValMap" >

                            <c:if test="${attValMap.getKey()=='refToObject'}">
                                <c:set var="messageId" value="${attValMap.getValue()}"/>
                            </c:if>

                            <c:if test="${attValMap.getKey()!='userId' && attValMap.getKey()!='refToObject'}">
                                    <c:forEach items="${attributesMessageType}" var="att">
                                        <c:if test="${att.label==attValMap.getKey()}">
                                            <c:set var="typeMes" value="${att.labelType}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${typeMes=='image'}">
                                        <img class="mt-2" style="height: 105px; width: 105px; display: block;"
                                             src="../img/${attValMap.getValue()==null ? 'no-image.jpg' : attValMap.getValue()}"/>
                                    </c:if>
                            <c:if test="${typeMes!='image'}">
                                <td>${attValMap.getKey()} : </td>
                                <td>${attValMap.getValue()}</td>
                            </c:if>
                                <br/>
                            </c:if>

                        </c:forEach>
                        </div>
                        <c:forEach items="${mes}" var="attValMap" >
                            <c:if test="${attValMap.getKey()=='userId'}">
                                <c:if test="${attValMap.getValue()==userAccount.id || role=='[ADMIN]'}">
                                    <div class="text-center"><td>
                                        <a href="/deleteMessage/${id}/${messageId}" class="mt-2 mb-1 ml-1 btn btn-dark" type="submit">Delete</a>
                                        <a href="/editMessage/${id}/${messageId}" class="mt-2 mb-1 ml-1 btn btn-dark" type="submit">Edit</a>
                                    </td></div>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </tr>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
