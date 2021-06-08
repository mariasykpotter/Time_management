<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<c:set var="unapprovedActivities" scope="request" value="${TimeLogDao.getInfoByStatus(param.id,0)}"></c:set>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="unapproved.jsp">Unapproved timelogs</a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <form action="/admin" method="post">
                        <button class="btn btn-light mt-3 ml-3" type="submit">Back</button>
                        <input type="hidden" name="command" value="admin"/>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
    <table class="table table-striped" id="unapproved">
        <thead>
        <tr>
            <th>firstName</th>
            <th>lastName</th>
            <th>userName</th>
            <th>activityName</th>
            <th>categoryName</th>
            <th>start</th>
            <th>end</th>
            <th>action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.unapprovedActivities}" var="list">
            <tr>
                <td><c:out value="${list.get(0)}"/></td>
                <td><c:out value="${list.get(1)}"/></td>
                <td><c:out value="${list.get(2)}"/></td>
                <td><c:out value="${list.get(3)}"/></td>
                <td><c:out value="${list.get(4)}"/></td>
                <td><c:out value="${list.get(5)}"/></td>
                <td><c:out value="${list.get(6)}"/></td>
                <td>
                    <form method="post" action="controller">
                        <button type="submit" class="btn btn-light">Approve</button>
                        <input type="hidden" value="approve" name="command"/>
                        <input type="hidden" value="${list.get(7)}" name="time_log_id"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
