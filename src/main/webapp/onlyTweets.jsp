<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>User Messages</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
        o        .blue {
            background-color: #00b5ec !important;
        }
    </style>
</head>
<body>
<div class="my-3 p-3 bg-white rounded box-shadow">
    <h6 class="border-bottom border-gray pb-2 mb-0">Recent updates</h6>
    <c:forEach items="${tweets}" var="tweet">
        <div class="media text-muted pt-3">
            <img src="<c:url value = "${tweet.author.avatar}"/>" alt="" class="mr-2 rounded" width="32"
                 height="32">

            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                <strong class="d-block text-gray-dark">
                        <a href="profileEdit?login=${tweet.author.login}"> ${tweet.author.login} </a>
                    <br>
                </strong>
                <strong class="d-block text-gray-dark"></strong>
                    ${tweet.message}
                <br><br>
                <i>Dothraki translation:</i>
                <br>
                    ${tweet.translation}
                </strong>
                <br><br>
                <b>Published at : <fmt:formatDate value="${tweet.publishedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></b>
            </p>
            <c:if test="${tweet.author.login.equals(sessionScope.login)}">
                <a href="deleteTweet?tweetId=${tweet.id}">Delete</a>
            </c:if>
        </div>
    </c:forEach>
</div>

</body>
</html>
