<%@ page import="cqu.web.model.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/11
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>hello</h1>
<div><a href="index.jsp">add</a></div>

<div>
    <table frame="box" rules="all">
        <% List<Student> list = (List)request.getAttribute("students"); %>
        <tr>
            <td>student id</td>
            <td>student name</td>
        </tr>

        <% for(Student stu:list){ %>
        <tr>
            <td>
                <%=stu.id%>
            </td>
            <td>
                <%=stu.name%>
            </td>
        </tr>
        <%}%>

    </table>
</div>
</body>
</html>
