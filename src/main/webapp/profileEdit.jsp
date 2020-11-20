<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Edit profile</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">


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

        body {
            margin-top: 20px;
            color: #1a202c;
            text-align: left;
            background-color: #e2e8f0;
        }

        .main-body {
            padding: 15px;
        }

        .card {
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
        }

        .card {
            position: relative;
            display: flex;
            flex-direction: column;
            min-width: 0;
            word-wrap: break-word;
            background-color: #fff;
            background-clip: border-box;
            border: 0 solid rgba(0, 0, 0, .125);
            border-radius: .25rem;
        }

        .card-body {
            flex: 1 1 auto;
            min-height: 1px;
            padding: 1rem;
        }

        .gutters-sm {
            margin-right: -8px;
            margin-left: -8px;
        }

        .gutters-sm > .col, .gutters-sm > [class*=col-] {
            padding-right: 8px;
            padding-left: 8px;
        }

        .mb-3, .my-3 {
            margin-bottom: 1rem !important;
        }

        .bg-gray-300 {
            background-color: #e2e8f0;
        }

        .h-100 {
            height: 100% !important;
        }

        .shadow-none {
            box-shadow: none !important;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="main-body">
        <%@include file="header.jsp" %>

        <c:if test="${errors != null}">
            <div class="alert alert-warning">
                <strong>${errors.header}</strong> <br>
                <p>${errors.message}<p>
            </div>
        </c:if>

<%-- AVATAR SECTION--%>
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="<c:url value = "${user.avatar}"/>" alt="Admin" class="rounded-circle" width="150">
                            <div class="mt-3">
                                <h4> ${user.name} ${user.lastName} </h4>
                                <p class="text-secondary mb-1">${user.login}</p>
                                <p class="text-secondary mb-1">Followers: ${fn:length(user.followers)}</p>

                                <button class="btn btn-primary">Follow</button>

                                <div class="btn-group">
                                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">
                                        Edit profile
                                    </button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="profileEdit?editField=login&type=text">Change
                                            login</a>
                                        <a class="dropdown-item" href="profileEdit?editField=name&type=text">Change
                                            name</a>
                                        <a class="dropdown-item" href="profileEdit?editField=surname&type=text">Change
                                            surname </a>
                                        <a class="dropdown-item" href="profileEdit?editField=email&type=email">Change
                                            email </a>
                                        <a class="dropdown-item" href="profileEdit?editField=avatar&type=file">Change
                                            avatar </a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="profileEdit?editField=password&type=password">Change
                                            password</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="deleteProfile" style="color: red"
                                           onclick="return confirm('Are you sure?')">DELETE ACCOUNT</a>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <%--CHANGE SECTION--%>

                        <c:if test="${editField != null}">
                            <hr>

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
                                            <div class="custom-file">
                                                <input name="avatar" accept="image/*" type="file" class="custom-file-input" id="inputGroupFile01">
                                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary btn-block blue"> Confirm</button>
                                    </div>
                                </form>
                            </c:if>

                        </c:if>


                    </div>
                </div>
            </div>



<%--PROFILE INFO SECTION--%>
            <div class="col-md-8">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Login</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.login}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.name}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Lastname:</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.lastName}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.email}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Dothraki since :</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <fmt:formatDate value="${user.registeredSince}" pattern="yyyy-MM-dd HH-mm"/>
                            </div>
                        </div>

                    </div>
                </div>
                <%--            MESSAGES SECTION--%>
                <div class="col-mb-3">
                    <%@include file="onlyTweets.jsp" %>
                </div>

            </div>



        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
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

