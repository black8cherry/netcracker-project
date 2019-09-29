<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>registration</title>
</head>
<body>



<form action="${pageContext.request.contextPath}/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>

</body>
</html>
