<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>request接收中文参数乱码问题</title>
</head>

<body>
<form action="<%=request.getContextPath()%>/servlet/RequestDemo04" method="get">
    用户名：<input type="text" name="userName"/>
    <input type="submit" value="post方式提交表单">
</form>
</body>
</html>