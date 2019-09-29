<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>editAttribute</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<div class="container">
        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input class="form-control mr-sm-2" type="text" name="label" placeholder="label"/>
            <button class="btn btn-success mr-sm-2" type="submit">Add attribute</button>
        </form>


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
<%--<table>

        <tr>
            <td></td>
            <td></td>
            &lt;%&ndash;<td><a href="${pageContext.request.contextPath}/editAttribute/deleteTypeAtt/${typeAtt.id}">delete</a> </td>
        &ndash;%&gt;
        </tr>
    </c:forEach>
</table>--%>

<a href="${pageContext.request.contextPath}/main">to main</a>
</body>
</html>