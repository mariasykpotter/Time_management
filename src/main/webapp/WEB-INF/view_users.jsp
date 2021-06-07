<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.TimeLogDao" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
    <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#users').dataTable();
        });
    </script>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <form action="/admin" method="post">
            <button class="btn btn-light mt-3 ml-3" type="submit">Back</button>
            <input type="hidden" name="command" value="admin">
        </form>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <form action="/register" method="post">
                        <button class="btn btn-light mt-3 ml-3" type="submit">Register admin</button>
                        <input type="hidden" name="command" value="register_admin">
                    </form>
                </li>
                <li class="nav-item mt-3 ml-3">
                    <input class="btn btn-primary" type="submit" form="checkbox_form" value="Delete users"/>
                </li>
            </ul>
        </div>
    </nav>
    <table class="table table-striped" id="users">
        <thead>
        <tr>
            <th>firstName</th>
            <th>lastName</th>
            <th>userName</th>
            <th>role</th>
            <th>activityNumber</th>
            <th>activityDuration</th>
            <th>action1</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${TimeLogDao.getInfoAboutUser()}" var="list">
            <tr>
                <td><c:out value="${list.get(0)}"/></td>
                <td><c:out value="${list.get(1)}"/></td>
                <td><c:out value="${list.get(2)}"/></td>
                <td><c:out value="${list.get(3)}"/></td>
                <c:choose>
                    <c:when test="${list.get(3).equals('user')}">
                        <td><c:out value="${list.get(4)}"/></td>
                        <td><c:out value="${list.get(5)}"/></td>
                        <td>
                            <input type="checkbox" name="users" value="${list.get(6)}" form="checkbox_form">
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                        <td></td>
                        <td></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="post" action="controller" id="checkbox_form">
        <input type="hidden" value="deleteUser" name="command"/>
    </form>
</div>
</body>
</html>
