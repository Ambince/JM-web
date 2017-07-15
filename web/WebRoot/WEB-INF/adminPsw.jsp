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
	<div>
		<table bgcolor="#F0F0F0">
			<tbody>
				<tr>
					<td class="font_red">【管理员 - 管理 - 修改密码】</td>
				</tr>
			</tbody>
		</table>
		&nbsp;
		<form action="AdminServlet" method="post">
			<table>
				<tbody>
					<tr align="center">
						<td width="40%">原密码 <input name="oldpassword" type="text" /></td>
						<td width="40%">新密码 <input name="newpassword" type="text" /></td>
						<td width="20%"><input name="submit" type="submit"
							value="修改密码" /></td>
					</tr>
				</tbody>
			</table>
			<input name="type" type="hidden" value="modify_psd" />
		</form>
	</div>
</body>
</html>
