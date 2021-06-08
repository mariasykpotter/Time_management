<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.ActivitiesDao" %>
<%@ page import="com.example.demo.dao.TimeLogDao" %>
<%@ page import="com.example.demo.model.Approve" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <meta name="description" content="Bootstrap">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
    <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#time_logs').dataTable();
        });
    </script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <form action="/activities" method="post">
            <button class="btn btn-light mt-3 ml-3" type="submit">Back</button>
            <input type="hidden" name="command" value="activities">
        </form>
    </nav>
    <table class="table table-striped" id="time_logs">
        <thead>
        <tr>
            <th>activityName</th>
            <th>categoryName</th>
            <th>startAt</th>
            <th>endAt</th>
            <th>duration</th>
            <th>status</th>
            <th>action1</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${TimeLogDao.getAllTimeLogsInfo()}" var="list">
            <tr>
                <form method="post" action="controller">
                    <td><select class="form-select" name="activity">
                        aria-label="Default select example">
                        <option selected>${list.get(0)}</option>
                        <c:forEach items="${ActivitiesDao.getAllActivitiesWithCategory(\"activity_name\")}"
                                   var="activity">
                            <option value="${activity.get(0)}">${activity.get(0)}</option>
                        </c:forEach>
                    </select></td>
                    <td><c:out value="${list.get(1)}"/></td>
                    <td><input type="text" name="start_at" value="${list.get(2)}"/></td>
                    <td><input type="text" name="end_at" value="${list.get(3)}"/></td>
                    <td><c:out value="${list.get(4)}"/></td>
                    <td><c:out value="${Approve.getStatus(Integer.valueOf(list.get(5)))}"/></td>
                    <td>
                        <button type="submit" class="btn btn-light">Edit timelog</button>
                        <input type="hidden" value="editTimelog" name="command"/>
                        <input type="hidden" value="${list.get(6)}" name="log_id"/>
                    </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
