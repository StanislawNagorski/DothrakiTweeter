<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>header</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <meta name="google-signin-client_id" content="464947380957-4his5r75p6q5i9oj3u4vl7jusg5lq3a1.apps.googleusercontent.com">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            background: #f5f5f5
        }

        .custom-text {
            color: #d2974f;
        }

        .bg-blue {
            background-color: black;
        }

        .box-shadow {
            box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05);
        }

        .tab {
            padding-left: 50px;
        }
    </style>
</head>
<body>

<div class="d-flex align-items-center p-3 my-3 text-white-50 bg-blue rounded box-shadow">
    <a  href="profileEdit">
        <img class="mr-3" src="<c:url value = "${avatar}"/>" alt="" width="48" height="48">
    </a>
    <div class="lh-100">
        <h6 class="mb-0 lh-100">
            <a class="custom-text" href="profileEdit">
                ${fn:toUpperCase(login)}
            </a>
        </h6>
    </div>
    <div class="lh-100">
        <h6 class="mb-0 lh-100 tab">
            <a class="custom-text" href="messages">Messages</a>
        </h6>
    </div>
    <div class="lh-100">
        <h6 class="mb-0 lh-100 tab">
            <a class="custom-text" href="users">Users</a>
        </h6>
    </div>
    <div class="lh-100 ml-auto">
        <h6 class="mb-0 lh-100">
            <a class="custom-text" onclick="signOut();" href="logout">Log out</a>
        </h6>
    </div>
</div>

<script>
    function signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
        });
    }

    function onLoad() {
        gapi.load('auth2', function() {
            gapi.auth2.init();
        });
    }
</script>


</body>
</html>
