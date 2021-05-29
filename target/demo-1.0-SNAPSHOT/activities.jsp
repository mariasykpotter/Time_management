<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page import="model.Person" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
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
                    <a class="nav-link" href="add_activity.jsp">Add activity</a>
                </li>
                <li class="nav-item">
                    <form action="/controller" method="post">
                        <button class="btn btn-primary" type="submit">Log out</button>
                        <input type="hidden" name="command" value="logout">
                    </form>
                </li>
            </ul>
        </div>
    </nav>
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>activityName</th>
            <th>category</th>
            <th>duration in hours</th>
            <th>action1</th>
            <th>action2</th>
        </tr>
        <c:forEach items="${sessionScope.activities_list}" var="list">
            <tr>
                <td><c:out value="${list.get(0)}"/></td>
                <td><c:out value="${list.get(1)}"/></td>
                <% List<String> lst = (List<String>) pageContext.getAttribute("list");%>
                <td><%= TimeLogDao.getSumDuration(((Person) session.getAttribute("user")).getId(), Integer.valueOf(lst.get(2)))%>
                </td>
                <td>
                    <form method="post" action="controller">
                        <button type="submit" class="btn btn-light">Add to timelog</button>
                        <input type="hidden" value="write" name="command"/>
                        <input type="hidden" value="${list.get(2)}" name="id"/>
                    </form>
                </td>
                <td>
                    <form method="post" action="controller">
                        <button type="submit" class="btn btn-light">Delete an activity</button>
                        <input type="hidden" value="delete_activity" name="command"/>
                        <input type="hidden" value="${list.get(2)}" name="id"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
