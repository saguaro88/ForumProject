<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Topics</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<%@include file="navbar.jsp" %>
<div class = "container-fluid">
<c:forEach items="${topicsList}" var="topic">
    <style>
        .card {display:inline-block;}
    </style>
<div class="card" style="width: 18rem;">
    <div class="card-body">
        <h5 class="card-title"><c:out value="${topic.title}" /></h5>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Created by: <c:out value="${topic.user.username}" /></li>
        <li class="list-group-item">Date: <fmt:formatDate value="${topic.creationDate}" pattern="dd-MM-yyyy HH:mm" /></li>
        <li class="list-group-item">Posts: <c:out value="${topic.posts.size()}" /></li>
    </ul>
    <div class="card-body">
        <a href="/topic/<c:out value='${topic.id}' />" class="btn btn-info" role="button">Enter</a>
        <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin">
        </sec:authorize>
        <c:if test="${(topic.user.username eq pageContext.request.userPrincipal.name) or (isAdmin eq true)}">
            <a href="/topic/update/<c:out value='${topic.id}' />" class="btn btn-success" role="button">Update</a>
            <a href="/topic/delete/<c:out value='${topic.id}' />" class="btn btn-danger" role="button">Delete</a>
        </c:if>

    </div>
</div>
</c:forEach>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>