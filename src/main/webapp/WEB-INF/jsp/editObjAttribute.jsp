<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/bg.css"%></style>
</head>
<body class="text-center">

<div class="container ">

    <div class="mx-auto" style="width: 300px;">

        <form class="" action="/editAttribute" >
            <button class="btn btn-dark mt-2" type="submit">Back</button>
        </form>

        <h3 >Attributes of ${type.typename}</h3>
        <table class="mt-2 mx-auto">
            <c:forEach items="${attributes}" var="att">
                <tr>
                    <td><span>${att.attribute.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${type.id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
        <h3 class="mt-5">Attributes which is not in ${type.typename} movie</h3>
        <table class="mt-2 mx-auto">
            <c:forEach items="${mAttributes}" var="att">
                <tr>
                    <td><span>${att.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${type.id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
                </tr>
            </c:forEach>
        </table>



    </div>



</div>

</body>
</html>
