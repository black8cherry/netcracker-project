<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/objType.css"%></style>

</head>
<body >
<div class="row">
    <div class="col">
        <div class="mx-auto" style="width: 200px;">

            <form action="${pageContext.request.contextPath}/main/administratorPanel" >
                <button class="btn btn-dark mt-2" type="submit">Back</button>
            </form>

            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/objType" method="post">
                <p><select style="background-color: #151515; width: 200px"  size="5" multiple name="parentId">
                    <option disabled>Choose type</option>
                    <c:forEach items="${types}" var="type">
                        <option style="color: aliceblue" value="${type.getId()}">${type.getTypename()}</option>
                    </c:forEach>
                </select></p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input class="form-control mt-4 mr-sm-2" type="text" name="typename"/>
                <button class="btn btn-success mt-4 mx-5" type="submit">Add type</button>
            </form>
        </div>
    </div>
    <div class="col  mt-4">
        <h3>Tree of objects</h3>
        <ul class="tree">

            <c:forEach var="type" items="${parentType}">
                <li>
                    <c:set var="type" value="${type}" scope="request"/>
                    <jsp:include page="tree.jsp"/>
                </li>
            </c:forEach>


        </ul>
    </div>
</div>

</body>
</html>
