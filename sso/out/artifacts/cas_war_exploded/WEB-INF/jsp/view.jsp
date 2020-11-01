<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CAS</title>
</head>
<body>
<h1>认证中心</h1>
<div>用户:${uname}</div>
<div><a href="http://localhost:8080/cas/logout">注销</a></div>
<div>
	<div>访问其他系统</div>
	<div><a href="http://localhost:8080/app1/view?uname=${uname}">系统A</a></div>
	<div><a href="http://localhost:8080/app2/view?uname=${uname}">系统B</a></div>
</div>
</body>
</html>