<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.demo.dao.CategoryDao" %>
<html>
<head>
    <title>Title</title>
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
<header>
    <ul style="float:right; margin-right:55px; overflow:hidden">
        <li style="list-style:none; display:inline-block; margin-right:5px">
            <a href="add_activity?lang=uk_UA" class="ua"><img
                    src="https://www.countryflags.io/ua/flat/32.png"></a>
        </li>
        <li style="list-style:none; display:inline-block">
            <a href="/add_activity?lang=en_US" class="us"><img
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
    <form action="/controller" method="post">
        <div class="form-group">
            <label for="activity_name" class="col-2 col-form-label"><fmt:message key="activity"
                                                                                 bundle="${bundle}"/></label>
            <div class="col-5">
                <input class="form-control" type="text" placeholder="<fmt:message key="category" bundle="${bundle}"/>"
                       name="activity" id="activity_name"
                       required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-2 col-form-label"><fmt:message key="category" bundle="${bundle}"/></label>
            <div class="col-5">
                <select class="form-select" name="category" aria-label="Default select example">
                    <option selected><fmt:message key="activity" bundle="${bundle}"/></option>
                    <c:forEach items="${CategoryDao.getAllCategories()}" var="category">
                        <option value="${category.getId()}">${category.getCategoryName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-sm-4">
                <input type="hidden" name="command" value="addActivity" required>
                <button type="submit" class="btn-md btn-primary"><fmt:message key="add_activity" bundle="${bundle}"/></button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
