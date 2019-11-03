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
    <div class="col mt-4 ml-2">
        <div class="mx-auto" style="width: 200px;">
            <form action="${pageContext.request.contextPath}/main/administratorPanel" >
                <button class="btn btn-dark mt-2" type="submit">Back</button>
            </form>

            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/objType" method="post">
                <p><select style="background-color: #151515; width: 200px"  size="5" multiple name="parentId">
                    <option disabled>Choose type</option>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.getId()}">${type.getTypename()}</option>
                    </c:forEach>
                </select></p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input style="background-color: #151515" class="form-control mt-4 mr-sm-2" type="text" name="typename"/>
                <button class="btn btn-success mt-4 mx-5" type="submit">Add type</button>
            </form>
        </div>
    </div>
    <div class="col-3  mt-4">
        <h4>Tree of objects</h4>
        <ul class="tree">

            <c:forEach var="type" items="${parentType}">
                <li>
                    <c:set var="type" value="${type}" scope="request"/>
                    <jsp:include page="tree.jsp"/>
                </li>
            </c:forEach>


        </ul>
    </div>

    <div class="col mt-4 mx-auto">
        <h4>Attributes (with parents)</h4>
        <table class="">
            <c:forEach items="${attributesWithParent}" var="att">
                <tr>
                    <td><span>${att.getLabel()}</span></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col mt-4 mx-auto">
        <h4 >Attributes of ${attributesType.typename}</h4>
        <table class="mt-2 mx-auto">
            <c:forEach items="${attributesInObject}" var="att">
                <tr>
                    <td><span>${att.attribute.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${attributesType.id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
        <h4 class="mt-5">Attributes which is not in ${attributesType.typename}</h4>
        <table class="mt-2 mx-auto">
            <c:forEach items="${attributesNotInObject}" var="att">
                <tr>
                    <td><span>${att.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${attributesType.id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="col mt-4 mr-2">
        <form class="my-2 my-lg-0" action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
            <input  type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input style="background-color: #151515" class="form-control mt-4 mb-3 mr-sm-2 inp"  type="text" name="label" placeholder="label"/>
            <p><select   size="4" multiple name="labelType">
                <option disabled>Choose limitations for attribute</option>
                <option  value="char">Only text</option>
                <option  value="numerical">Only numbers</option>
                <option  value="multiType">Without limitations</option>
            </select></p>
            <button class="btn btn-success mt-1 mr-sm-2" type="submit">Add attribute</button>
        </form>

        <h4>All attributes</h4>
        <table align="center">
            <c:forEach  items="${allAttributes}" var="att">
                <tr>
                    <td><span class="mr-2">${att.label}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editAttribute/delete/${att.label}">delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>


</body>
</html>
