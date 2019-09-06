<%--
  Created by IntelliJ IDEA.
  User: 79200
  Date: 01.09.2019
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>registration</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/registration" method="post">
  <div class="container">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>
      <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter username" name="username" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" required>
<%--
    <label for="psw-repeat"><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
    <hr>
--%>
    <button type="submit" class="registerbtn">Register</button>
  </div>

  <div class="container signin">
    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>.</p>
  </div>

</form>
<%--
<form action="${pageContext.request.contextPath}/registration" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>
--%>
</body>
</html>
