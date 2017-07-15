<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
body{
	background:#99D9EA;
	font-size:16px;
	margin:0;
}
h1{
	font-size: 26px;
	color: #333;
	height: 30px;
	padding: 10px;
	line-height: 30px;
	float: left;
}
span{
	font-size: 16px;
	color: #333;
	height: 30px;
	padding: 10px;
	line-height: 30px;
	float: right;
}
</style>
</head>

<body>
<h1>家萌管理中心</h1>
<span>欢迎，管理员：${sessionScope.admin}。<a href="AdminServlet?type=logout" target="_top">注销</a></span>
</body>
</html>
