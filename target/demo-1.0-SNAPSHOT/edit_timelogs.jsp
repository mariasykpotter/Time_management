<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.ActivitiesDao" %>
<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page import="model.Approve" %>
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

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="activities.jsp">Back to main page</a>
                </li>
            </ul>
        </div>
    </nav>
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>activityName</th>
            <th>categoryName</th>
            <th>startAt</th>
            <th>endAt</th>
            <th>duration</th>
            <th>status</th>
            <th>action1</th>
        </tr>
        <c:forEach items="${TimeLogDao.getAllTimeLogsInfo()}" var="list">
            <tr>
                <form method="post" action="controller">
                    <td><select class="form-select" name="activity" aria-label="Default select example">
                        <option selected>${list.get(0)}</option>
                        <c:forEach items="${ActivitiesDao.getAllActivities()}" var="activity">
                            <option value="${activity.getId()}">${activity.getActivityName()}</option>
                        </c:forEach>
                    </select></td>
                    <td><c:out value="${list.get(1)}"/></td>
                    <td><input type="text" name="start_at" value="${list.get(2)}"/></td>
                    <td><input type="text" name="end_at" value="${list.get(3)}"/></td>
                    <td><c:out value="${list.get(4)}"/></td>
                    <td><c:out value="${Approve.getStatus(Integer.valueOf(list.get(5)))}"/></td>
                    <td>
                        <button type="submit" class="btn btn-light">Edit timelog</button>
                        <input type="hidden" value="edit_timelog" name="command"/>
                        <input type="hidden" value="${list.get(6)}" name="log_id"/>
                    </td>
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
