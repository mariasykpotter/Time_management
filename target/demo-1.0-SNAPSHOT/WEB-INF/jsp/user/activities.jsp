<%@ page import="com.example.demo.dao.ActivitiesDao" %>
<%@ page import="com.example.demo.dao.Constants" %>
<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page import="com.example.demo.model.Person" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
    <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script>$(document).ready(function () {
        $('#activities').dataTable();
    });
    </script>
</head>
<body>
<header>
    <ul style="float:right; margin-right:55px; overflow:hidden">
        <li style="list-style:none; display:inline-block; margin-right:5px">
            <a href="/activities?lang=uk_UA" class="ua"><img
                    src="https://www.countryflags.io/ua/flat/32.png"></a>
        </li>
        <li style="list-style:none; display:inline-block">
            <a href="/activities?lang=en_US" class="us"><img
                    src="https://www.countryflags.io/us/flat/32.png"></a>
        </li>
    </ul>
</header>
<c:if test="${param.lang!=null}">
    <c:set var="locale" value="${param.lang}" scope="session"/>
</c:if>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources" var="bundle"/>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><fmt:message key="navbar.logo" bundle="${bundle}"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <form action="/add_activity" method="post">
                        <button class="btn btn-light mt-3 ml-3" type="submit"><fmt:message key="add_activity"
                                                                                           bundle="${bundle}"/></button>
                        <input type="hidden" name="command" value="add_activity">
                    </form>
                </li>
                <li class="nav-item">
                    <form action="/edit_timelogs" method="post">
                        <button class="btn btn-light mt-3 ml-3" type="submit"><fmt:message key="edit_timelogs"
                                                                                           bundle="${bundle}"/></button>
                        <input type="hidden" name="command" value="edit_timelogs">
                    </form>
                </li>
                <li>
                    <form action="/controller" method="post">
                        <button class="btn btn-primary mt-3 ml-3" type="submit"><fmt:message key="navbar.log_out"
                                                                                             bundle="${bundle}"/></button>
                        <input type="hidden" name="command" value="logout">
                    </form>
                </li>
                <li class="nav-item mt-3 ml-3">
                    <input class="btn btn-primary" type="submit" form="checkbox_form"
                           value="<fmt:message key="navbar.delete_activities" bundle="${bundle}"/>"/>
                </li>
            </ul>
        </div>
    </nav>
    <table class="table table-striped" id="activities">
        <thead>
        <tr>
            <th><fmt:message key="activity" bundle="${bundle}"/></th>
            <th><fmt:message key="category" bundle="${bundle}"/></th>
            <th><fmt:message key="duration" bundle="${bundle}"/></th>
            <th><fmt:message key="delete" bundle="${bundle}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME)}" var="list">
            <tr>
                <td>
                    <form method="post" action="write">
                        <input type="submit" class="btn btn-light" value="${list.get(0)}"/>
                        <input type="hidden" name="command" value="write"/>
                        <input type="hidden" value="${list.get(2)}" name="id"/>
                    </form>
                </td>
                <td><c:out value="${list.get(1)}"/></td>
                <% List<String> lst = (List<String>) pageContext.getAttribute("list");%>
                <td><%= TimeLogDao.getSumDuration(((Person) session.getAttribute("user")).getId(), Integer.valueOf(lst.get(2)))%>
                </td>
                <td>
                    <input type="checkbox" name="activities" value="${list.get(2)}" form="checkbox_form">
                        <%--                    <form method="post" action="com.example.demo.controller">--%>
                        <%--                        <button type="submit" class="btn btn-light">Delete an activity</button>--%>
                        <%--                        <input type="hidden" value="deleteActivity" name="command"/>--%>
                        <%--                        <input type="hidden" value="${list.get(2)}" name="id"/>--%>
                        <%--                    </form>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="post" action="controller" id="checkbox_form">
        <input type="hidden" value="deleteActivity" name="command"/>
    </form>
</div>
</body>
</script>
</html>
