<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 01.09.2019
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>filmList</title>
    <style>
        ul.hr {
            margin: 0px;
            padding: 4px;
        }
        ul.hr li {
            display: inline-block;
            margin-right: 5px;
        }
    </style>
</head>
<body>
<div>
    Object!
</div>
<div>
    <ul class="hr">

        <li>
            <table>
                <c:forEach  items="${att}" var = "att">
                    <tr>
                        <td>${att.label}</td>
                    </tr>
                </c:forEach>
            </table>
        </li>

        <li>
            <table>
                <c:forEach  items="${val}" var = "val">
                    <tr>
                        <td>${val.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </li>
    </ul>

</div>

<div>
    <table>
        <c:forEach items="${fl}" var="fl">
            <tr>
                <td>${fl.label}</td>
                <td>${fl.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<form action="${pageContext.request.contextPath}/main" method="post">
    <input type="text" name="objectName" value="${objects.name}"/>
    <input type="text" name="objectId" value="${objects.id}"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}}"/>
    <button type="submit">save</button>
</form>
<a href="${pageContext.request.contextPath}/main" >link to main</a>
</body>
</html>
