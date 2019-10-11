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
    <div class="col-5">
        <form  action="${pageContext.request.contextPath}/addFilm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input class="form-control" type="text" name="name" placeholder="Name"/><hr>


            <p><select size="5" multiple name="type">
                <option disabled>Choose object type</option>
                <c:forEach items="${types}" var="type">
                    <option value="${type.getType()}">${type.getType()}</option>
                </c:forEach>
            </select></p>



            <input class="form-control" type="file" name="file"/>
            <button class="mt-5 btn btn-success mr-sm-2" type="submit">add</button>
        </form>
    </div>
    <div class="col">
        <form action="${pageContext.request.contextPath}/main">
            <button class="mt-5 btn btn-success mr-sm-2" type="submit">To main page</button>
        </form>
    </div>
    </div>
</div>

<div class="album py-5 bg-dark">
    <div class="container">
        <div class="row">
            <c:forEach  items="${objects}" var ="object">
                <div class="col-md-3">
                    <div class="card mb-3 shadow-sm">
                        <img class="card-img-top" style="height: 225px; width: 100%; display: block;"
                             src="img/${object.filename}">
                        <div class="card-body  mx-auto">
                            <p class="card-text">
                            Id : ${object.id}<hr>
                            Name : ${object.name}<hr>
                            Type : ${object.type.type}
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
