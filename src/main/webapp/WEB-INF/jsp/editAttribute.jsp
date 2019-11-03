<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>editAttribute</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/editAttribute.css"%></style>
</head>
<body class="">
    <div class="row ">
        <div class="col col-center" >
            <form action="/main/administratorPanel">
                <button class="btn btn-dark" type="submit" >Back</button>
            </form>
            <form class="my-2 my-lg-0" action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
                <input  type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input class="form-control mt-4 mb-3 mr-sm-2 inp"  type="text" name="label" placeholder="label"/>
                <p><select   size="4" multiple name="labelType">
                    <option disabled>Choose limitations for attribute</option>
                    <option  value="char">Only text</option>
                    <option  value="numerical">Only numbers</option>
                    <option  value="multiType">Without limitations</option>
                </select></p>
                <button class="btn btn-success mt-1 mr-sm-2" type="submit">Add attribute</button>
            </form>

            <h3>All attributes</h3>
            <table align="center">
            <c:forEach  items="${attributes}" var="att">
                <tr>
                    <td><span class="mr-2">${att.label}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editAttribute/delete/${att.label}">delete</a></td>
                </tr>
            </c:forEach>
            </table>
            <h3>Attribute list of movie types</h3>
            <table align="center">
            <c:forEach items="${typeAttributes}" var="typeAtt">
                <tr>
                    <td><span class="mr-2">${typeAtt.type.getTypename()} </span></td>
                    <td><span>${typeAtt.attribute.getLabel()}</span></td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </div>

</body>
</html>