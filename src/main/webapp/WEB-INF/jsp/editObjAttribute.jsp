<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

<div class="container ">

    <div class="container">
        <div class="row">

            <div class="col">
                <h3>NetFilms</h3>
            </div>

            <div class="col" >
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

    <div class="mx-auto" style="width: 300px;">
        <h3 class="mt-5">Attributes of object type</h3>
        <table class="mt-2 ml-5">
            <c:forEach items="${attributes}" var="att">
                <tr>
                    <td>${att.attribute.getLabel()}</td>
                    <td><a href="${pageContext.request.contextPath}/main/${id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
        <h3 class="mt-5">Attributes which is not in object type</h3>
        <table class="mt-2 ml-5">
            <c:forEach items="${mAttributes}" var="att">
                <tr>
                    <td>${att.getLabel()}</td>
                    <td><a href="${pageContext.request.contextPath}/main/${id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>



</div>

</body>
</html>
