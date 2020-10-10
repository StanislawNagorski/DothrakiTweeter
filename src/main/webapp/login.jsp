<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>


<html>
<head>
    <title>Log in to Twitter</title>
</head>
<body>
<p>Log In to enjoy :) </p>

<c:if test="${errors != null}">

    <c:forEach items="${errors}" var="error">
        <div class="alert alert-warning">
            <strong>${error.header}</strong> <br>
            <p>${error.message}<p>
        </div>
    </c:forEach>
</c:if>


<form action="login" method="post">
    <input name="login" placeholder="login" type="text" required>
    <br>
    <input name="password" placeholder="password" type="password" required>
    <br>
    <input id="remember" name="remember" type="checkbox">
    <label for="remember">Remember?</label>
    <br>
    <button type="submit">LogIn</button>

</form>
</body>
</html>
