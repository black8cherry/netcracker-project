<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetFilms</title>
    <style><%@include file="../css/messages.css"%></style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="bg-modal">
    <div class="modal-content" style="width: 400px; background-color: #5f9ea0">

        <form class="mt-4 mx-2" action="/main/${id}" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <c:forEach items="${attributesMessageType}" var="att">
                <c:choose>

                    <c:when test="${att.label=='userId' || att.label=='refToObject'}">
                        <input type="hidden" name="label" value="${att.label}">
                        <input type="hidden" name="value" value="${userAccount.id}">
                    </c:when>

                    <c:when test="${att.label=='username'}">
                        <input type="hidden" name="label" value="${att.label}">
                        <input type="hidden" name="value" value="${userAccount.username}">
                    </c:when>

                    <c:when test="${att.labelType=='image'}">
                        <div class="upload-btn-wrapper">
                            <input type="file" name="file"/>
                        </div>
                        <input type="hidden" name="label" value="${att.label}">
                        <input type="hidden" name="value" value="111"/>
                    </c:when>

                    <c:otherwise>
                        <input type="hidden" name="label" value="${att.label}">
                        <input type="text"
                               id="values"
                               class="form-control mb-2"
                               pattern="${att.labelType=='numerical' ? '[0-9]+' : '[A-Za-z0-9]+'}"
                               name="value"
                               placeholder="${att.label}">
                        <span class="error" aria-live="polite"></span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <button  class="btn btn-dark mb-2 " type="submit">Add new review</button>

        </form>
    </div>
</div>
<script src="../js/filmList.js"></script>
</body>
</html>
