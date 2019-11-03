<%@ page import="com.source.project.domain.Type" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form style="background-color: #151515; width: 150px;" class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/objType" method="get">
    <input type="hidden" name="typeId" value="${type.id}"/>
    <input style="border: 0; background-color: #151515; color: aliceblue        " type="submit" value="${type.typename}" />
    <a href="${pageContext.request.contextPath}/delObjType/${type.id}">delete</a>
</form>


<ul>
    <%
        List<Type> childList = (List<Type>) request.getAttribute("childType");
        Type type = (Type) request.getAttribute("type");
        Type oldType;
        for (Type typeL: childList
             ) {

            if(type.getId()==typeL.getParentId()) {
                %><li><%
                oldType = type;
                request.setAttribute("type", typeL);
                %><jsp:include page="tree.jsp"/><%
                request.setAttribute("type", oldType);
                %></li><%
            }

        }
    %>
</ul>
