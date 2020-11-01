<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CAS Login</title>
</head>
<body>
<p>${msg}</p>
<form action="login" method="post">
<p>登录名：<input type="text" name="name"></p>
<p>密码：<input type="password" name="passwd"></p>
<p>来自域：<input type="text" name="LOCAL_SERVICE" value="${LOCAL_SERVICE}" readonly></p>
<p><input type="submit" value="登录"></p>
</form>
</body>
</html>