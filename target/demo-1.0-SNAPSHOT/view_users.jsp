<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page import="model.Role" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="registration.jsp">Register admin user</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="admin.jsp">Back to main page</a>
                </li>
            </ul>
        </div>
    </nav>
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>firstName</th>
            <th>lastName</th>
            <th>userName</th>
            <th>role</th>
            <th>activityNumber</th>
            <th>activityDuration</th>
            <th>action1</th>
        </tr>
        <c:forEach items="${TimeLogDao.getInfoAboutUser()}" var="list">
            <tr>
                <td><c:out value="${list.get(0)}"/></td>
                <td><c:out value="${list.get(1)}"/></td>
                <td><c:out value="${list.get(2)}"/></td>
                <td><c:out value="${list.get(3)}"/></td>
                <c:if test="${list.get(3).equals('user')}">
                <td><c:out value="${list.get(4)}"/></td>
                <td><c:out value="${list.get(5)}"/></td>
                <td>
                        <form method="post" action="controller">
                            <button type="submit" class="btn btn-light">Delete user</button>
                            <input type="hidden" value="delete_user" name="command"/>
                            <input type="hidden" value="${list.get(6)}" name="user_id"/>
                        </form>
                </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
