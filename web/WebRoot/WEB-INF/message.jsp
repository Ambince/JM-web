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

</head>

<body>
 
	<p>${message}</p>
<!--
	<p>
		页面将在${wait}秒后跳转。。。<a href="${url}">立即跳转</a>
	</p>
-->
	<p>
		自动立刻跳转。。。<a href="${url}">没有响应点击跳转</a>
	</p>
</body>
<script>
	//var time = ${wait}*1000;
	//window.setTimeout("window.location.href='${url}'", time);
	window.location.href='${url}';
</script>
</html>
