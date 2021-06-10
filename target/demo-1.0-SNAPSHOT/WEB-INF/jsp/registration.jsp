<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Account</title>
    <link href="https://fonts.googleapis.com/css?family=ZCOOL+XiaoWei" rel="stylesheet">
    <link href="../../css/style1.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<header>
    <ul style="float:right; margin-right:55px; overflow:hidden">
        <li style="list-style:none; display:inline-block; margin-right:5px">
            <a href="/register?lang=uk_UA" class="ua"><img
                    src="https://www.countryflags.io/ua/flat/32.png"></a>
        </li>
        <li style="list-style:none; display:inline-block">
            <a href="/register?lang=en_US" class="us"><img
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
    <div class="regbox box">
        <img class="avatar" src="../../images/collaboration.png">
        <h1><fmt:message key="register.title" bundle="${bundle}"/></h1>
        <form action="/controller" method="post">
            <p><fmt:message key="register.name" bundle="${bundle}"/></p>
            <input type="text" placeholder="Surname and name" name="full_name" required>
            <p><fmt:message key="username" bundle="${bundle}"/></p>
            <input type="text" placeholder="userName" name="user_name" required>
            <p><fmt:message key="password" bundle="${bundle}"/></p>
            <input type="password" placeholder="password" name="password" required>
            <input type="hidden" name="command" value="register">
            <input type="submit" value="Register">
            <a href="/login"><fmt:message key="register.link" bundle="${bundle}"/></a>
        </form>
    </div>
</div>
</body>
</html>
