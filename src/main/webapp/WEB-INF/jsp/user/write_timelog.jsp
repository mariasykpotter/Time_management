<%@ page import="com.example.demo.dao.ActivityDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.demo.dao.ActivityDao" %>
<%@ page import="com.example.demo.model.Activity" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<header>
    <ul style="float:right; margin-right:55px; overflow:hidden">
        <li style="list-style:none; display:inline-block; margin-right:5px">
            <a href="/write?lang=uk_UA" class="ua"><img
                    src="https://www.countryflags.io/ua/flat/32.png"></a>
        </li>
        <li style="list-style:none; display:inline-block">
            <a href="/write?lang=en_US" class="us"><img
                    src="https://www.countryflags.io/us/flat/32.png"></a>
        </li>
    </ul>
</header>
<c:if test="${param.lang!=null}">
    <c:set var="locale" value="${param.lang}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources" var="bundle"/>
<c:if test="${sessionScope.message !=null}">
    <div class="alert alert-info alert-dismissible fade show">
        <strong>Info!</strong>${sessionScope.message}
        <button type="button" class="close" data-dismiss="alert">&times;</button>
    </div>
</c:if>
<c:if test="${param.id!=null}">
    <c:set var="activity" scope="session" value="${ActivityDao.getById(param.id)}"></c:set>
</c:if>
<div class="container">
    <form action="/controller" method="post">
        <div class="form-group">
            <label for="name" class="col-2 col-form-label"><fmt:message key="activity" bundle="${bundle}"/></label>
            <div class="col-5">
                <input class="form-control" type="text" placeholder="<fmt:message key="activity" bundle="${bundle}"/>"
                       name="activity" id="name"
                       value="${sessionScope.activity.getActivityName()}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="start" class="col-2 col-form-label"><fmt:message key="start" bundle="${bundle}"/></label>
            <div class="col-5">
                <input class="form-control" type="time" placeholder="<fmt:message key="start" bundle="${bundle}"/>"
                       name="start" id="start">
            </div>
            <input type="hidden" name="command" value="addLog" required>
        </div>
        <div class="form-group">
            <label for="example-time-input" class="col-2 col-form-label"><fmt:message key="end"
                                                                                      bundle="${bundle}"/></label>
            <div class="col-5">
                <input class="form-control" type="time" placeholder="<fmt:message key="end" bundle="${bundle}"/>"
                       name="end"
                       id="example-time-input">
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-sm-4">
                <button type="submit" class="btn-md btn-primary"><fmt:message key="add_timelog"
                                                                              bundle="${bundle}"/></button>
                <input type="hidden" name="command" value="addLog"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
