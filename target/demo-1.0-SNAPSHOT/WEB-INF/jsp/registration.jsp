<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Account</title>
    <link href="https://fonts.googleapis.com/css?family=ZCOOL+XiaoWei" rel="stylesheet">
    <link href="../../css/style1.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container">
    <div class="regbox box">
        <img class="avatar" src="../../images/collaboration.png">
        <h1>
            Register Account</h1>
        <form action="/controller" method="post">
            <p>
                Surname and name:</p>
            <input type="text" placeholder="Surname and name" name="full_name" required>
            Username: </p>
            <input type="text" placeholder="userName" name="user_name" required>
            <p></p>Password:</p>
            <input type="password" placeholder="password" name="password" required>
            <input type="hidden" name="command" value="register">
            <input type="submit" value="Register">
            <a href="/login">Already have Account?</a>
        </form>
    </div>
</div>
</body>
</html>
