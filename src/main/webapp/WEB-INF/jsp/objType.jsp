<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
</head>
<body class="text-center">
<div class="container ">
    <div class="mx-auto" style="width: 200px;">

        <form action="${pageContext.request.contextPath}/main" >
            <button class="btn btn-dark mt-2" type="submit">Return to main page</button>
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
            <button class="btn btn-success mt-4 ml-4" type="submit">Add type</button>
        </form>
    </div>
    <div class="album mt-3 py-5 bg-dark" >
        <div class="container">
            <div class="row">
                <c:forEach  items="${parentType}" var ="pt">
                    <div class="col-md-4">
                        <div class="card mb-3 shadow-sm" style="background-color: #151515">
                            <div class="card-body  mx-auto">
                                <p class="card-text">
                                    <span>Parent type : ${pt.typename}</span>
                                    <a href="${pageContext.request.contextPath}/delObjType/${pt.id}">delete</a> <br/>
                                    <span>Child types : </span><br/>
                                    <c:forEach items="${childType}" var="ct">
                                        <c:if test="${ct.parentId == pt.id}">
                                            - ${ct.typename}
                                            <a href="${pageContext.request.contextPath}/delObjType/${ct.id}">delete</a>
                                            <br/>
                                        </c:if>
                                    </c:forEach>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
