<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<div class="container">
    <form action="/controller" method="post">
        <div class="form-group">
            <label for="activity_name" class="col-2 col-form-label">Activity name</label>
            <div class="col-5">
                <input class="form-control" type="text" placeholder="Activity name" name="activity" id="activity_name"
                       required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-2 col-form-label">Category name</label>
            <div class="col-5">
                    <select class="form-select" name = "category" aria-label="Default select example">
                        <option selected>Category name</option>
                        <c:forEach items="${CategoryDao.getAllCategories()}" var="category">
                        <option value="${category.getId()}">${category.getCategoryName()}</option>
                        </c:forEach>
                    </select>
            </div>
        </div>
        <div class="form-group row">
            <div class="offset-sm-4">
                <input type="hidden" name="command" value="add_activity" required>
                <button type="submit" class="btn-md btn-primary">Add activity</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
