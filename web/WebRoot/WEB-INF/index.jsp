<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link rel="stylesheet" type="text/css" href="css.css">
</head>
<!-- 
<frameset rows="50,*" cols="*" frameborder="no" border="0"
	framespacing="0" id="main">
	<frame name="top" noresize src="AdminServlet?type=top" scrolling="auto">
-->
	<frameset cols="160px,*">
		<frame name="left" noresize src="AdminServlet?type=left"
			scrolling="auto">
		<frame name="right" src="AdminServlet?type=welcome" scrolling="auto">
	</frameset>
<!-- 
</frameset>
-->
<noframes></noframes>
</html>
