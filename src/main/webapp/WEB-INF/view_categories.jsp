<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.demo.dao.CategoryDao" %>
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
            $('#categories').dataTable();
        });
    </script>
</head>
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
                    <form class="form-inline mt-3" method="post" action="controller">
                        <input class="form-control mr-sm-2" type="text" name="categoryName"
                               placeholder="Category name" required>
                        <input type="hidden" value="addCategory" name="command"/>
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Add category</button>
                    </form>
                </li>
                <li class="nav-item mt-3 ml-3">
                    <input class="btn btn-primary" type="submit" form="checkbox_form" value="Delete categories"/>
                </li>
            </ul>
        </div>
    </nav>
    <table class="table table-striped" id="categories">
        <thead>
        <tr>
            <th>categoryName</th>
            <th>edit</th>
            <th>delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${CategoryDao.getAllCategories()}" var="item">
            <tr>
                <form method="post" action="controller">
                    <td><input type="text" value="${item.getCategoryName()}" name="category"/></td>
                    <td>
                        <button type="submit" class="btn btn-light">Edit category</button>
                        <input type="hidden" value="editCategory" name="command"/>
                        <input type="hidden" value="${item.getId()}" name="category_id"/>
                    </td>
                </form>
                <td>
                    <input type="checkbox" name="categories" value="${item.getId()}" form="checkbox_form">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="post" action="controller" id="checkbox_form">
        <input type="hidden" value="deleteCategory" name="command"/>
    </form>
</div>
</body>
</html>
