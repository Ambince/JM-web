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
<script>
function OnClick(){
	var n = document.getElementById("id");
	var f = document.getElementById("file");
	if(n.value == "" || f.value == ""){
		window.alert("未输入数据！");
		return false;	
	}		
	return window.confirm("确认发布新版本？");
}
</script>
<body>
	<div>
		<table bgcolor="#F0F0F0">
			<tbody>
				<tr>
					<td width="100%" class="font_red">【Android版本管理】</td>
				</tr>
			</tbody>
		</table>
		&nbsp;
		<table>
			<tbody>
				<tr class="font_b">
					<td width="20%">当前版本</td>
					<td width="20%">发布日期</td>
					<td width="60%">更新包地址</td>
				</tr>
				<tr>
					<td>
						<p>${version}</p>
					</td>
					<td>${date}</td>
					<td>${url} </td>
				</tr>
			</tbody>
		</table>
		&nbsp;
		<table bgcolor="#F0F0F0">
			<tbody>
				<tr>
					<td width="100%" class="font_red">发布新版本</td>
				</tr>
			</tbody>
		</table>
		<form action="VersionAdminServlet" enctype="multipart/form-data" method="post" >	
		<table>
			<tbody>
				<tr>					
					<td width="10%" class="font_b">版本号：</td>					
					<td width="20%"><input type="text" id="id" name="id"/></td>
					<td width="10%" class="font_b">APK文件：</td>
					<td width="40%"><input type="file" id="file" name="file"/></td>
					<td width="20%"><input type="submit" onclick="return OnClick()" value="发布"/></td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
</body>
</html>
