<%@ page import="com.example.demo.dao.ActivitiesDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.ActivitiesDao" %>
<%@ page import="model.Activity" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<c:if test="${requestScope.message ==null}">
    <div class="alert alert-info alert-dismissible fade show">
        <strong>Info!</strong>${requestScope.message}
        <button type="button" class="close" data-dismiss="alert">&times;</button>
    </div>
</c:if>
<c:set var="activity" scope="session" value="${ActivitiesDao.getById(param.id)}"></c:set>
<div class="container">
    <form action="/controller" method="post">
        <div class="form-group">
            <label for="name" class="col-2 col-form-label">Activity name</label>
            <div class="col-5">
                <input class="form-control" type="text" placeholder="Activity name" name="activity" id="name"
                       value="${sessionScope.activity.getActivityName()}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="start" class="col-2 col-form-label">Start time</label>
            <div class="col-5">
                <input class="form-control" type="time" placeholder="Start time" name="start" id="start">
            </div>
            <input type="hidden" name="command" value="addLog" required>
        </div>
        <div class="form-group">
            <label for="example-time-input" class="col-2 col-form-label">End time</label>
            <div class="col-5">
                <input class="form-control" type="time" placeholder="End time" name="end"
                       id="example-time-input">
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-sm-4">
                <button type="submit" class="btn-md btn-primary">Add a timelog</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
