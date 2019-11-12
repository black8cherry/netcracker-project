<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>editAttribute</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style><%@include file="../css/editAttribute.css"%></style>
</head>
<body class="container">
    <div class="row ">
        <div class="col mt-4" >
            <form action="${pageContext.request.contextPath}/editAttribute/edit">
                <input type="hidden" name="attributeId" value="${attribute.id}"/>
                <input class="mt-2 form-control inp" type="text" name="label" value="${attribute.label}"/>
                <input type="hidden" name="oldLabelType" value="${attribute.labelType}"/>
                <p><select style="background-color: #151515"  size="5" multiple name="labelType">
                    <option disabled>Choose attribute type</option>
                        <option style="color: aliceblue" value="char">char</option>
                        <option style="color: aliceblue" value="numerical">numerical</option>
                </select></p>
                <input type="hidden" name="typeId" value="${typeId}"/>
                <button class="btn btn-dark mt-2" type="submit">Save</button>
            </form>
        </div>
    </div>
</body>
</html>