<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Edit profile</title>
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

        <c:if test="${errors != null}">
            <div class="alert alert-warning">
                <strong>${errors.header}</strong> <br>
                <p>${errors.message}<p>
            </div>
        </c:if>

        <h6 class="border-bottom border-gray pb-2 mb-0">Your account</h6>

        <div class="media text-muted pt-3">

            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                <strong class="d-block text-gray-dark">
                    On twitter since :
                    <fmt:formatDate value="${user.registeredSince}" pattern="yyyy-MM-dd HH-mm"/> </strong>

                <strong class="d-block text-gray-dark">Login: ${user.login}</strong>
                <a href="profileEdit?editField=login&type=text"><b>Change login</b></a>

                <strong class="d-block text-gray-dark">Name: ${user.name}</strong>
                <a href="profileEdit?editField=name&type=text"><b>Change name</b></a>

                <strong class="d-block text-gray-dark">Surname: ${user.lastName}</strong>
                <a href="profileEdit?editField=surname&type=text"><b>Change lastname </b></a>

                <strong class="d-block text-gray-dark">email: ${user.email}</strong>
                <a href="profileEdit?editField=email&type=email"><b>Change email </b></a>
                <br>
                <a href="profileEdit?editField=password&type=password"><b>Change password </b></a>
                <br>
                <strong class="d-block text-gray-dark">Avatar:
                    <img src="<c:url value = "${user.avatar}"/>" alt=""
                         class="mr-2 rounded" width="32" height="32">
                </strong>
                <a href="profileEdit?editField=avatar&type=file"><b>Change avatar </b></a>
                <br>
                <br>
                <a href="deleteProfile" onclick="return confirm('Are you sure?')"> <b>DELETE ACCOUNT </b></a>

            </p>
        </div>

        <br>

    <c:if test="${editField != null}">

        <c:if test="${!editField.equals('avatar')}">
            <form action="profileEdit" method="POST" onsubmit="return checkPasswordIdentity(this)">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                        </div>
                        <input name="${editField}" class="form-control" placeholder="Enter new ${editField}"
                               type="${type}"
                               required>
                    </div>
                </div>

                <c:if test="${editField.equals('password')}">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                            </div>
                            <input name="repeatedPassword" class="form-control" placeholder="Repeat password"
                                   type="password" required>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block blue"> Confirm</button>
                </div>
            </form>
        </c:if>

        <c:if test="${editField.equals('avatar')}">

        <form action="profileEdit" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                    </div>
                    <input name="avatar" class="form-control" accept="image/*" type="file" required>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block blue"> Confirm</button>
            </div>
        </form>
        </c:if>

    </c:if>

    </div>

</main>
<script>
    function checkPasswordIdentity(form) {
        if (form.password.value !== form.repeatedPassword.value) {
            alert("Password are not equal");
            return false;
        }
        return true;
    }
</script>
</body>
</html>

