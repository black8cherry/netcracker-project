<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

<div class="container ">

    <div class="mx-auto" style="width: 300px;">
        <h3 class="mt-5">Object attributes</h3>
        <table class="mt-2 ml-5">
            <c:forEach items="${attributes}" var="att">
                <tr>
                    <td>${att.attribute.getLabel()}</td>
                    <td><a href="${pageContext.request.contextPath}/main/${id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
        <h3 class="mt-5">Attributes which is not in object</h3>
        <table class="mt-2 ml-5">
            <c:forEach items="${mAttributes}" var="att">
                <tr>
                    <td>${att.getLabel()}</td>
                    <td><a href="${pageContext.request.contextPath}/main/${id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
                </tr>
            </c:forEach>
        </table>
        <a class="mt-5" href="${pageContext.request.contextPath}/main">to main</a>
        <button class="mt-5 bt-primary"  >Main page</button>
    </div>






</div>

</body>
</html>
