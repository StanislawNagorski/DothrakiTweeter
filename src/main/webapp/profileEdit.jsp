<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Edit profile</title
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <style>
        body {
            background: #f5f5f5
        }

        .border-bottom {
            border-bottom: 1px solid #e5e5e5;
        }

        .box-shadow {
            box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05);
        }
    </style>
</head>
<body>

<main role="main" class="container">

    <%@include file="header.jsp" %>

    <div class="my-3 p-3 bg-white rounded box-shadow">
        <h6 class="border-bottom border-gray pb-2 mb-0">Your account</h6>

        <div class="media text-muted pt-3">

            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                <strong class="d-block text-gray-dark">
                    On twitter since :
                    <fmt:formatDate value="${user.registeredSince}" pattern="yyyy-MM-dd HH-mm"/> </strong>

                <strong class="d-block text-gray-dark">Login: ${user.login}</strong>
                <strong class="d-block text-gray-dark">Name: ${user.name}</strong>
                <a href="#"><b>Change name</b></a>
                <strong class="d-block text-gray-dark">Surname: ${user.lastName}</strong>
                <a href="#"><b>Change lastname </b></a>
                <strong class="d-block text-gray-dark">email: ${user.email}</strong>
                <a href="#"><b>Change email </b></a>
                <br>
                <a href="#"><b>Change password </b></a>
                <strong class="d-block text-gray-dark">Avatar:
                    <img src="<c:url value = "${user.avatar}"/>" alt=""
                         class="mr-2 rounded" width="32" height="32">
                </strong>
                <a href="#"><b>Change avatar </b></a>
            </p>
        </div>

    </div>

</main>

</body>
</html>

