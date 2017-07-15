<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	margin: 0;
	padding: 0;
	background: #ddd;
}
#login{
	height: 350px;
	width: 450px;
	background: #444;
	margin: 130px auto 0px auto;
}
#login h2{
	margin: 0;
	padding: 5px;
	font-size: 26px;
	color: #222;
	background: #C8BFE7;
	text-align: center;
}
#login form{
	margin-top: 0px;
}
#login .row{
	height: 30px;
	line-height: 30px;
	font-size: 18px;
	padding:10px;
	text-align: center;
	color: #eee;
}
</style>
  </head>
  <body>
    <div id="login">
	<h2>家萌后台管理登陆</h2>
	<form action="AdminLoginServlet" method="post">
		<div class="row" style="color:pink;margin:0;">${msg }</div>
		<div class="row">
			<span>用户名：</span>
			<input name="username" type="text">
		</div>
		<div class="row">
			<span>密　码：</span>
			<input name="password" type="password">
		</div>
 
		<div class="row">
			<input type="checkbox" name="autologin" value="true" checked/>30天内自动登陆
		</div>
		<div class="row">
			<input name="submit" type="submit" value="登陆">
		</div>
		<input name="type" type="hidden" value="login">
	</form>
</div>
<script>
window.onload = function() {
	//防止在frame里面出现登录页面  
	if (top.location !== self.location) { 
		top.location.href = self.location.href;
	}
}
</script>
</body>
</html>
