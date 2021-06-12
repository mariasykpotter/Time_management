<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/mytag/mytag.tld"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
    <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#unapproved').dataTable();
        });
    </script>
</head>
<body>
<header>
    <ul style="float:right; margin-right:55px; overflow:hidden">
        <li style="list-style:none; display:inline-block; margin-right:5px">
            <a href="/unapproved?lang=uk_UA" class="ua"><img
                    src="https://www.countryflags.io/ua/flat/32.png"></a>
        </li>
        <li style="list-style:none; display:inline-block">
            <a href="/unapproved?lang=en_US" class="us"><img
                    src="https://www.countryflags.io/us/flat/32.png"></a>
        </li>
    </ul>
</header>
<c:if test="${param.id!=null}">
<c:set var="unapprovedActivities" scope="session" value="${TimeLogDao.getInfoByStatus(param.id,0)}"></c:set>
</c:if>
<c:if test="${param.lang!=null}">
    <c:set var="locale" value="${param.lang}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources" var="bundle"/>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="unapproved.jsp"><fmt:message key="navbar.title" bundle="${bundle}"/></a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <form action="/admin" method="post">
                        <button class="btn btn-light mt-3 ml-3" type="submit"><fmt:message key="back"
                                                                                           bundle="${bundle}"/></button>
                        <input type="hidden" name="command" value="admin"/>
                    </form>
                </li>
                <li class="nav-item mt-3 ml-3">
                    <input class="btn btn-primary" type="submit" form="checkbox_form"
                           value="<fmt:message key="delete" bundle="${bundle}"/>"/>
                </li>
            </ul>
        </div>
    </nav>
    <table class="table table-striped" id="unapproved">
        <thead>
        <tr>
            <th><fmt:message key="user.firstName" bundle="${bundle}"/></th>
            <th><fmt:message key="user.lastName" bundle="${bundle}"/></th>
            <th><fmt:message key="username" bundle="${bundle}"/></th>
            <th><fmt:message key="activity" bundle="${bundle}"/></th>
            <th><fmt:message key="category" bundle="${bundle}"/></th>
            <th><fmt:message key="start" bundle="${bundle}"/></th>
            <th><fmt:message key="end" bundle="${bundle}"/></th>
            <th><fmt:message key="action1" bundle="${bundle}"/></th>
            <th><fmt:message key="action2" bundle="${bundle}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.unapprovedActivities}" var="list">
            <tr>
                <tag:table num="6" list="${list}"></tag:table>
                <td>
                    <form method="post" action="controller">
                        <button type="submit" class="btn btn-light"><fmt:message key="approve"
                                                                                 bundle="${bundle}"/></button>
                        <input type="hidden" value="approve" name="command"/>
                        <input type="hidden" value="${list.get(7)}" name="time_log_id"/>
                    </form>
                </td>
                <td>
                    <input type="checkbox" name="timelogs" value="${list.get(7)}" form="checkbox_form">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="post" action="controller" id="checkbox_form">
        <input type="hidden" value="deleteLog" name="command"/>
    </form>
</div>
</body>
</html>
