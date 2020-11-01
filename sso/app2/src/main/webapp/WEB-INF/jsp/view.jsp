<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>System B</title>
</head>
<body>

<h1>欢迎来到系统B</h1 >
<div>用户:${uname }</div>
<div><a href="logout">注销</a></div>
<div>
	<div>访问其他系统</div>
	<div><a href="http://localhost:8080/cas/?uname=${uname}">认证中心</a></div>
	<div><a href="http://localhost:8080/app1/?uname=${uname}">系统A</a></div>
</div>

</body>
</html>