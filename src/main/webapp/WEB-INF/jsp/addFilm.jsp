<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>NetFilms</title>
    <%--<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="../js/addFilm.js"></script>--%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
    <style><%@include file="../css/addFilm.css"%></style>
</head>
<body >
<div class="container">
    <div class="row">
    <div class="col-5">
        <form  action="${pageContext.request.contextPath}/addFilm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input style="background-color: #151515"  class="form-control" type="text" name="name" placeholder="Name"/><hr>


            <p><select style="background-color: #151515"  size="5" multiple name="type">
                <option disabled>Choose movie type</option>
                <c:forEach items="${types}" var="type">
                    <option style="color: aliceblue" value="${type.getTypename()}">${type.getTypename()}</option>
                </c:forEach>
            </select></p>



            <div class="upload-btn-wrapper">
                <button class="btn-up ">Upload a file</button>
                <input type="file" name="file" />
            </div> <br/>

            <button class="mt-3 btn btn-dark mr-sm-2" type="submit">Add</button>
        </form>
    </div>
    <div class="col">
        <form action="${pageContext.request.contextPath}/main/administratorPanel">
            <button class="mt-5 btn btn-success mr-sm-2" type="submit">Back</button>
        </form>
    </div>
    </div>
</div>

<div class="album py-5 bg-dark">
    <div class="container">
        <div class="row">
            <c:forEach  items="${movie}" var ="movie">
                <div class="col-md-3">
                    <div style="background-color: #151515" class="card mb-3 shadow-sm">
                        <img class="card-img-top" style="height: 225px; width: 100%; display: block;"
                             src="img/${movie.filename}">
                        <div class="card-body  mx-auto">
                            <p class="card-text">
                                <span>Id : ${movie.id}</span><hr>
                                <span>Name : ${movie.name}</span><hr>
                                <span>Type : ${movie.type.typename}</span>
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
