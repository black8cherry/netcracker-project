<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a class="link" href="/editObjectAttributes/${type.typename}">${type.typename}</a>
<a href="${pageContext.request.contextPath}/delObjType/${type.id}">delete</a>

<ul>
    <c:forEach var="innerType" items="${childType}">

        <c:if test="${type.id==innerType.parentId}">
            <li>
                <c:set var="oldType" value="${type}" scope="request"/>
                <c:set var="type" value="${innerType}" scope="request"/>
                <jsp:include page="tree.jsp"/>

                <c:set var="type" value="${oldType}" scope="request"/>
            </li>
        </c:if>
        <%--<c:if test="${type.id!=innerType.parentId}">
            <c:set var="type" value="${oldType}"/>
            <jsp:include page="tree.jsp"/>
        </c:if>--%>
    </c:forEach>
</ul>
