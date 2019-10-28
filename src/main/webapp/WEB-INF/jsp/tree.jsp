<%@ page import="com.source.project.domain.Type" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a class="link" href="/editObjectAttributes/${type.id}">${type.typename}</a>
<a href="${pageContext.request.contextPath}/delObjType/${type.id}">delete</a>

<ul>
    <%
        List<Type> childList = (List<Type>) request.getAttribute("childType");
        Type type = (Type) request.getAttribute("type");
        Type oldType;
        //System.out.println("list : "+childList);
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
