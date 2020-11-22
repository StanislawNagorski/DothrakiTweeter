<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>Welcome</title>
</head>
<body>


<!--  forward to index page for login if user info is not in session  -->
<% if (session.getAttribute("userName") == null) {%>
<jsp:forward page="/login.jsp"/>
<% } %>

<h3>Welcome  ${userName}</h3>

</body>
</html>