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

<title>添加管理员</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css.css">

<script type="text/javascript">
	function changeImg(img) {
		img.src = img.src + "?time=" + new Date().getTime();
	}
	function checkForm() {
		var canSub = true;
		//1.非空校验
		canSub = checkNull("username", "用户名不能为空!") && canSub;
		canSub = checkNull("password", "密码不能为空!") && canSub;
		canSub = checkNull("password2", "确认密码不能为空!") && canSub;
		canSub = checkNull("valistr", "验证码不能为空!") && canSub;

		//2.两次密码一致的校验
		var psw1 = document.getElementsByName("password")[0].value;
		var psw2 = document.getElementsByName("password2")[0].value;
		if (psw1 != psw2) {
			document.getElementById("password2_msg").innerHTML = "<font color='red'>两次密码不一致!</font>";
			canSub = false;
		}

		return canSub;

	}
	function checkNull(name, msg) {
		document.getElementById(name + "_msg").innerHTML = "";
		var objValue = document.getElementsByName(name)[0].value;
		if (objValue == null || objValue == "") {
			document.getElementById(name + "_msg").innerHTML = "<font color='red'>"
					+ msg + "</font>";
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<c:if test="${sessionScope.admin == null}">
	请登陆<a href="/login.jsp">登录</a>
	</c:if>
	<c:if test="${sessionScope.admin != null}">
	<div align="center">
		<table bgcolor="#F0F0F0">
			<tbody>
				<tr>
					<td class="font_red">【管理员 - 管理 - 修改密码】</td>
				</tr>
			</tbody>
		</table>
		<form action="/AdminServlet" method="POST"
			onsubmit="return checkForm()">
			<table style="width:300px;">
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="username"
						value="${param.username }" /></td>
					<td id="username_msg"></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="password" /></td>
					<td id="password_msg"></td>
				</tr>
				<tr>
					<td>确认密码:</td>
					<td><input type="password" name="password2" /></td>
					<td id="password2_msg"></td>
				</tr>
				<tr>
					<td>验证码:</td>
					<td><input type="text" name="valistr" /></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3" id="valistr_msg">${msg }</td>
				</tr>
				<tr>
					<td><input type="submit" value="注册用户" /></td>
					<td><img src="/ValiImg" onclick="changeImg(this)"
						style="cursor: pointer;" /></td>
				</tr>
				
			</table>
			<input name="type" type="hidden" value="regist" />
		</form>
	</div>
	</c:if>
</body>
</html>
