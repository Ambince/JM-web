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

<link rel="stylesheet" type="text/css" href="css.css">


</head>

<body>
	<table bgcolor="#F0F0F0">
		<tbody>
			<tr>
				<td width="10%" class="font_red">【会员统计】</td>
				<td width="30%" align="center">总注册数：<span class="font_red">${memsum}</span></td>
				<td width="30%" align="center">女：<span class="font_red">${memsumf}</span></td>
				<td width="30%" align="center">男：<span class="font_red">${memsumm}</span></td>
			</tr>
		</tbody>
	</table>

	&nbsp;

	<table>
		<tbody>
			<tr>
				<td align="center">今日登录用户数：<span class="font_red">${todayloginmem}</span></td>
				<td align="center">最近30天登录用户数：<span class="font_red">${mouthloginmem}</span></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
