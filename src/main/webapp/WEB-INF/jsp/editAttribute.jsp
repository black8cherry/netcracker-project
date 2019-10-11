<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>editAttribute</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container">

    <div class="row">
        <div class="col">
            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input class="form-control mt-4 mr-sm-2" type="text" name="label" placeholder="label"/>
                <button class="btn btn-success mt-4 mr-sm-2" type="submit">Add attribute</button>
            </form>
        </div>

        <div class="col">

            <c:forEach items="${types}" var="type">
                <form action="/editObjectAttributes/${type.getType()}">
                    <button class="btn btn-dark mt-4 " type="submit" >Edit ${type.getType()} attributes</button>
                </form>
            </c:forEach>
            
        </div>

        <div class="col">
            <form action="/main/administratorPanel">
                <button class="btn btn-success mt-4" type="submit">Back</button>
            </form>
        </div>
    </div>

    <h3>All attributes</h3>
    <c:forEach  items="${attributes}" var="att">
        <div class="row">
            <div class="col-md-2">
                    ${att.label}
            </div>
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/editAttribute/delete/${att.label}">delete</a>
            </div>
        </div>
    </c:forEach>

    <h3>Attribute list of object types</h3>
    <c:forEach items="${typeAttributes}" var="typeAtt">
        <div class="row">
            <div class="col-md-2">
                    ${typeAtt.type.getType()}
            </div>
            <div class="col-md-2">
                    ${typeAtt.attribute.getLabel()}
            </div>
        </div>
    </c:forEach>


</div>

</body>
</html>