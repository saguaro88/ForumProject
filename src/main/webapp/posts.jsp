<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Topics</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<style>
    body {
        margin: 0;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-size: .88rem;
        font-weight: 400;
        line-height: 1.5;
        color: #495057;
        text-align: left;
        background-color: #eef1f3
    }

    .mt-100 {
        margin-top: 100px
    }

    .card {
        box-shadow: 0 0.46875rem 2.1875rem rgba(4, 9, 20, 0.03), 0 0.9375rem 1.40625rem rgba(4, 9, 20, 0.03), 0 0.25rem 0.53125rem rgba(4, 9, 20, 0.05), 0 0.125rem 0.1875rem rgba(4, 9, 20, 0.03);
        border-width: 0;
        transition: all .2s
    }

    .card-header:first-child {
        border-radius: calc(.25rem - 1px) calc(.25rem - 1px) 0 0
    }

    .card-header {
        display: flex;
        align-items: center;
        border-bottom-width: 1px;
        padding-top: 0;
        padding-bottom: 0;
        padding-right: .625rem;
        height: 3.5rem;
        text-transform: uppercase;
        background-color: #fff;
        border-bottom: 1px solid rgba(26, 54, 126, 0.125)
    }

    .btn-primary {
        color: #fff;
        background-color: #3f6ad8;
        border-color: #3f6ad8
    }

    .btn {
        font-size: 0.8rem;
        font-weight: 500;
        outline: none !important;
        position: relative;
        transition: color 0.15s, background-color 0.15s, border-color 0.15s, box-shadow 0.15s
    }

    .card-body {
        flex: 1 1 auto;
        padding: 1.25rem
    }

    .card-body p {
        font-size: 13px
    }

    a {
        color: #E91E63;
        text-decoration: none !important;
        background-color: transparent
    }

    .media img {
        width: 40px;
        height: auto
    }
</style>
<%@include file="navbar.jsp" %>
    <div class="container-fluid mt-100">
        <c:forEach items="${postsList}" var="post">
        <div class="row">
            <div class="col-md-12">
                <div class="card mb-4">
                    <div class="card-header">
                        <div class="media flex-wrap w-100 align-items-center">
                            <div class="media-body ml-3"> <a href="javascript:void(0)" data-abc="true"><c:out value="${post.user.username}"/></a>
                                <div class="text-muted small">
                                    <fmt:formatDate value="${post.creationDate}" pattern="dd-MM-yyyy HH:mm" />
                                </div>
                            </div>
                            <div class="text-muted small ml-3">
                                <div>Member since <strong>
                                    <fmt:formatDate value="${post.user.createdAt}" pattern="dd-MM-yyyy" />
                                </strong></div>
                                <div><strong><c:out value="${post.user.postCounter}"/></strong> posts</div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                      <p><c:out value="${post.content}"/></p>
                    </div>
                    <div class="card-footer d-flex flex-wrap justify-content-between align-items-center">
                        <div class="px-4 pt-3">
                            <%
                                String topicId = (String) request.getAttribute("javax.servlet.forward.request_uri");
                                topicId = topicId.replaceAll("\\D+","");
                            %>
                            <c:set var="topicid" value="<%=topicId%>" />
                        <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin">
                        </sec:authorize>
                        <c:if test="${(post.user.username eq pageContext.request.userPrincipal.name) or (isAdmin eq true)}">
                            <a href="/post/update/${topicid}/<c:out value='${post.id}' />" class="btn btn-success" role="button">Update</a>
                            <a href="/post/delete/${topicid}/<c:out value='${post.id}' />" class="btn btn-danger" role="button">Delete</a>
                        </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>