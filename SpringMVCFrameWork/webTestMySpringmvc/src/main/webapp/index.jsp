<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/11
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
    <script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
<h1>index</h1>
<a href="show.do">Show student</a>
<hr>
<h3>student</h3>
<form action="students/get" method="get">
    <input type="submit" value="get">
</form>
<form action="students/post" method="post">
    <table>
        <tr>
            <td>
                id:
            </td>
            <td>
                <input type="number" name="id" required="required">
            </td>
        </tr>
        <tr>
            <td>
                name:
            </td>
            <td>
                <input type="text" name="name" required="required">
            </td>
        </tr>
    </table>
    <input type="submit" value="add">

</form>

<form action="students/delete" method="post">
    <table>
        <tr>
            <td>
                id:
            </td>
            <td>
                <input type="number" name="id" required="required">
            </td>
        </tr>
    </table>
    <input type="submit" value="delete">
</form>
<hr>
<h3>文件上传</h3>
<form action="file/post" enctype="multipart/form-data" method="post">
    <input type="file" name="file" required="required"><br/>
    <input type="submit">
</form>
<hr>

</body>
</html>
