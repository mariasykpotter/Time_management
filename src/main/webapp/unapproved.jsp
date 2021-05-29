<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<table border="1" class="table table-striped table-bordered">
    <tr class="thead-dark">
        <th>firstName</th>
        <th>lastName</th>
        <th>userName</th>
        <th>activityName</th>
        <th>categoryName</th>
        <th>action</th>
    </tr>
    <c:forEach items="${requestScope.unapproved}" var="list">
        <tr>
            <td><c:out value="${list.get(0)}"/></td>
            <td><c:out value="${list.get(1)}"/></td>
            <td><c:out value="${list.get(2)}"/></td>
            <td><c:out value="${list.get(3)}"/></td>
            <td><c:out value="${list.get(4)}"/></td>
            <td>
                <form method="post" action="controller">
                    <button type="submit" class="btn btn-light">Approve</button>
                    <input type="hidden" value="approve" name="command"/>
                    <input type="hidden" value="${list.get(5)}" name="time_log_id"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
