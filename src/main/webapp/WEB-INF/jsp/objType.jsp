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
                <span>Choose type : </span>
                <p><select style=" width: 200px;"  size="1" name="parentId">
                    <option value="${null}">parent type</option>
                    <c:forEach items="${types}" var="type">
                        <option value="${type.getId()}">${type.getTypename()}</option>
                    </c:forEach>
                </select></p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input placeholder="enter type" class="form-control mr-sm-2" type="text" name="typename"/>
                <button class="btn btn-success mt-3 " type="submit">Add type</button>
            </form>


        <h5 class="mt-4">Tree of object types</h5>
        <ul class="tree">

            <c:forEach var="type" items="${parentType}">
                <li>
                    <c:set var="type" value="${type}" scope="request"/>
                    <jsp:include page="tree.jsp"/>
                </li>
            </c:forEach>


        </ul>    </div>
    </div>

    <div class="col mt-4 mx-auto">
        <h5>Attributes of ${attributesType.typename}
            <c:if test="${attributesType.typename==null}">
                (choose type)
            </c:if>
            (with parent attributes) :
        </h5>
        <table class="">
            <c:forEach items="${attributesWithParent}" var="att">
                <tr>
                    <td><span class="mr-2">${att.getLabel()}</span></td>
                    <td><span>${att.labelType}</span></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="col mt-4 mx-auto">
        <h5 >Attributes of ${attributesType.typename}
            <c:if test="${attributesType.typename==null}">
            (choose type)
            </c:if>
        </h5>
        <table class="mt-2 mx-auto">
            <c:forEach items="${attributesInObject}" var="att">
                <tr>
                    <td><span>${att.attribute.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${attributesType.id}/deleteTypeAtt/${att.attribute.getLabel()}">Delete</a> </td>
                </tr>
            </c:forEach>
        </table>
        <h5 class="mt-5">Attributes which are not in ${attributesType.typename}</h5>
        <table class="mt-2 mx-auto">
            <c:forEach items="${attributesNotInObject}" var="att">
                <tr>
                    <td><span>${att.getLabel()}</span></td>
                    <td><a href="${pageContext.request.contextPath}/editObjectAttributes/${attributesType.id}/addTypeAtt/${att.getLabel()}">Add</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="col mt-2 mr-2">
        <form class="my-2 my-lg-0" action="${pageContext.request.contextPath}/editAttribute" method="post" enctype="multipart/form-data">
            <input  type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="typeId" value="${attributesType.id}">
            <input   class="form-control mt-4 mb-3 mr-sm-2 inp"  type="text" name="label" placeholder="label"/>
            <span>Choose type of attribute :</span>
            <p><select  size="1" style="width: 200px;" name="labelType">
                <option value="char">text</option>
                <option value="numerical">numerical</option>
                <option value="date">date</option>
                <option value="image">image</option>
            </select></p>
            <button class="btn btn-success mt-1 mr-sm-2" type="submit">Add attribute</button>
        </form>

        <h5>All attributes</h5>
        <table align="center">
            <c:forEach  items="${allAttributes}" var="att">
                <tr>

                        <form class="form-inline " action="${pageContext.request.contextPath}/editAttribute" method="get">
                            <input type="hidden" name="typeId" value="${attributesType.id}">
                            <input type="hidden" name="attributeId" value="${att.id}">
                            <td><input type="submit" value="${att.label}"></td>
                        </form>
                            <form action="${pageContext.request.contextPath}/editAttribute/delete/${att.id}">
                                <input type="hidden" name="typeId" value="${attributesType.id}">
                            <td><input class="link-blue" type="submit" value="delete"></td>
                            </form>

                </tr>
            </c:forEach>
        </table>
    </div>
</div>


</body>
</html>
